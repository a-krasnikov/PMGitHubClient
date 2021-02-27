package krasnikov.project.pmgithubclient.app.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmgithubclient.databinding.RecyclerLoadStateFooterBinding
import krasnikov.project.pmgithubclient.utils.PagedList
import krasnikov.project.pmgithubclient.utils.State

abstract class PagedListAdapter<T, VH : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var currentList = emptyList<T>()

    private val listSize
        get() = currentList.size

    private var endOfPaginationReached: Boolean = false

    private val stateListener: (State<List<T>, Exception>) -> Unit = {
        when (it) {
            is State.Loading -> {
                currentState = LoadState.Loading
                notifyItemChanged(itemCount - 1)
            }
            is State.Content -> {
                val startPosition = listSize
                val itemCount = it.data.size - startPosition
                if (itemCount != 0) {
                    currentList = it.data
                    currentState = LoadState.NotLoading
                    notifyItemRangeChanged(startPosition, itemCount)
                } else {
                    endOfPaginationReached = true
                    currentState = LoadState.NotLoading
                    notifyItemChanged(itemCount - 1)
                }
            }
            is State.Error -> {
                currentState = LoadState.Error(it.error)
                notifyItemChanged(itemCount - 1)
            }
        }
    }

    private var currentState: LoadState = LoadState.NotLoading

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

