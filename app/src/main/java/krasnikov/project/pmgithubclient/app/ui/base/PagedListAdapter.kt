package krasnikov.project.pmgithubclient.app.ui.base

import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmgithubclient.utils.PagedList
import krasnikov.project.pmgithubclient.utils.Result

abstract class PagedListAdapter<T, VH : RecyclerView.ViewHolder>(protected val items: PagedList<T>) :
    RecyclerView.Adapter<VH>() {

    init {
        loadNextData()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (position == itemCount - 1)
            loadNextData()
    }

    private fun loadNextData() {
        items.onLoadMore {
            when (it) {
                is Result.Success -> {
                    notifyItemRangeInserted(itemCount, it.data)
                }
                is Result.Error -> {
                    //TODO error
                }
            }
        }
    }
}
