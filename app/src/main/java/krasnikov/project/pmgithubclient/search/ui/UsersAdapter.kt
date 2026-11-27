package krasnikov.project.pmgithubclient.search.ui

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmgithubclient.app.ui.base.PagedListAdapter
import krasnikov.project.pmgithubclient.databinding.RecyclerItemUserBinding
import krasnikov.project.pmgithubclient.databinding.RecyclerLoadStateFooterBinding
import krasnikov.project.pmgithubclient.userinfo.data.model.User
import krasnikov.project.pmgithubclient.utils.load
import java.io.IOException
import java.lang.Exception

class UsersAdapter(loadNextData: () -> Unit) :
    PagingAdapter<User, UsersAdapter.UserViewHolder>(
        loadNextData = loadNextData,
        diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.login == newItem.login
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

        }) {

    override fun onCreateDataViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            RecyclerItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, item: User) {
        holder.bind(item)
    }

    class UserViewHolder(private val binding: RecyclerItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            with(binding) {
                ivAvatar.load(user.avatarUrl)
                tvLogin.text = user.login
            }
        }
    }
}

abstract class PagingAdapter<T, VH : RecyclerView.ViewHolder>(
    private val offsetStartLoadMore: Int = 4,
    private val loadNextData: () -> Unit,
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    private var currentState: PaginationState = PaginationState.Loading
        set(value) {
            val previousState = field
            field = value

            if (previousState is PaginationState.Complete) {
                mainHandler.post { notifyItemInserted(itemCount - 1) }
            } else {
                when (value) {
                    is PaginationState.Complete -> {
                        mainHandler.post { notifyItemRemoved(itemCount) }
                    }
                    is PaginationState.Error, PaginationState.Loading -> {
                        mainHandler.post { notifyItemChanged(itemCount - 1) }
                    }
                }
            }
        }

    var onItemClickListener: (item: T) -> Unit = {}


    // list size + LoadStateViewHolder
    final override fun getItemCount(): Int =
        if (currentState is PaginationState.Complete) super.getItemCount() else super.getItemCount() + 1

    final override fun getItemViewType(position: Int): Int =
        if (position == itemCount - 1 && currentState !is PaginationState.Complete)
            VIEW_TYPE_STATE
        else getDataViewType(position)

    final override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_STATE -> LoadStateViewHolder.create(parent) { loadNextData() }
            else -> onCreateDataViewHolder(parent, viewType)
        }
    }

    @Suppress("UNCHECKED_CAST")
    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LoadStateViewHolder -> {
                holder.bind(currentState)
            }
            else -> {
                onBindViewHolder(holder as VH, getItem(position))
                holder.itemView.setOnClickListener { onItemClickListener(getItem(position)) }
            }
        }

        // start loading when reach certain position
        if (position == itemCount - offsetStartLoadMore && currentState is PaginationState.Complete) {
            loadNextData()
        }
    }

    fun submitData(data: PaginationData<T>) {
        currentState = data.currentState
        submitList(data.availableData)
    }

    open fun getDataViewType(position: Int): Int {
        return VIEW_TYPE_DATA
    }

    abstract fun onCreateDataViewHolder(parent: ViewGroup, viewType: Int): VH

    abstract fun onBindViewHolder(holder: VH, item: T)

    private companion object {
        const val VIEW_TYPE_STATE = 0
        const val VIEW_TYPE_DATA = 1

        val mainHandler: Handler = Handler(Looper.getMainLooper())
    }

    class LoadStateViewHolder(
        private val binding: RecyclerLoadStateFooterBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry() }
        }

        fun bind(state: PaginationState) {
            if (state is PaginationState.Error) {
                binding.errorMsg.text = state.error.localizedMessage
            }
            binding.progressBar.isVisible = state is PaginationState.Loading
            binding.btnRetry.isVisible = state is PaginationState.Error
            binding.errorMsg.isVisible = state is PaginationState.Error
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
                val binding = RecyclerLoadStateFooterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return LoadStateViewHolder(binding, retry)
            }
        }
    }

}

sealed class PaginationState {
    object Complete : PaginationState()

    object Loading : PaginationState()

    data class Error(val error: Exception) : PaginationState()
}

class PaginationData<T>(
    val availableData: List<T>,
    val currentState: PaginationState
) {
    fun stateLoading(): PaginationData<T> =
        PaginationData(availableData, PaginationState.Loading)

    fun stateComplete(newData: List<T>): PaginationData<T> =
        PaginationData(availableData.union(newData).toList(), PaginationState.Complete)

    fun stateError(error: Exception): PaginationData<T> =
        PaginationData(availableData, PaginationState.Error(error))
}


fun main() {

    val previousState: PaginationState = PaginationState.Loading
    val value: PaginationState = PaginationState.Loading

    println(previousState::class == value::class)

}
