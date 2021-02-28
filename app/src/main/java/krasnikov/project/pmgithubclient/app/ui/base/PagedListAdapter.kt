package krasnikov.project.pmgithubclient.app.ui.base

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmgithubclient.databinding.RecyclerLoadStateFooterBinding
import krasnikov.project.pmgithubclient.utils.PagedList
import krasnikov.project.pmgithubclient.utils.State

abstract class PagedListAdapter<T, VH : RecyclerView.ViewHolder>(private val rvHandler: Handler) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var currentList = emptyList<T>()
        set(value) {
            val startPosition = listSize
            val countNewItems = value.size - startPosition
            if (countNewItems != 0) {
                field = value
                currentState = LoadState.NotLoading
                rvHandler.post { notifyItemRangeChanged(startPosition, countNewItems) }
            } else {
                endOfPaginationReached = true
                currentState = LoadState.NotLoading
            }
        }

    private val listSize
        get() = currentList.size

    private var endOfPaginationReached: Boolean = false

    private val stateListener: (State<List<T>, Exception>) -> Unit = {
        when (it) {
            is State.Loading -> {
                currentState = LoadState.Loading
            }
            is State.Content -> {
                currentList = it.data
            }
            is State.Error -> {
                currentState = LoadState.Error(it.error)
            }
        }
    }

    private var currentState: LoadState = LoadState.NotLoading
        set(value) {
            field = value
            rvHandler.post { notifyItemChanged(itemCount - 1) }
        }

    var pagedList: PagedList<T>? = null
        set(value) {
            field = value
            field?.stateLoadedListener = stateListener
            loadNextData()
        }

    var onItemClickListener: (item: T) -> Unit = {}

    // list size + LoadStateViewHolder
    final override fun getItemCount(): Int = currentList.size + 1

    final override fun getItemViewType(position: Int): Int =
        when (position) {
            itemCount - 1 -> VIEW_TYPE_STATE
            else -> VIEW_TYPE_DATA
        }

    final override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_STATE) {
            LoadStateViewHolder.create(parent) { loadNextData() }
        } else {
            onCreateViewHolder(parent)
        }
    }

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == listSize - 1)
            loadNextData()

        when (holder) {
            is LoadStateViewHolder -> {
                holder.bind(currentState)
            }
            else -> {
                @Suppress("UNCHECKED_CAST")
                onBindViewHolder(holder as VH, getItem(position))
                holder.itemView.setOnClickListener {
                    onItemClickListener(getItem(position))
                }
            }
        }
    }

    fun getItem(position: Int) = currentList.elementAt(position)

    private fun loadNextData() {
        if (!endOfPaginationReached) {
            currentState = LoadState.Loading
            pagedList?.onLoadMore()
        }
    }

    abstract fun onCreateViewHolder(parent: ViewGroup): VH

    abstract fun onBindViewHolder(holder: VH, item: T)

    private companion object {
        const val VIEW_TYPE_STATE = 0
        const val VIEW_TYPE_DATA = 1
    }

    sealed class LoadState {
        object NotLoading : LoadState()

        object Loading : LoadState()

        data class Error(val error: Exception) : LoadState()
    }

    class LoadStateViewHolder(
        private val binding: RecyclerLoadStateFooterBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.btnRetry.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
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

