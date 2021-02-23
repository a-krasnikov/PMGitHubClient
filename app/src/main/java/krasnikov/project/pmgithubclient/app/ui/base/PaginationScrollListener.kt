package krasnikov.project.pmgithubclient.app.ui.base

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    private var curPage = 1
    protected var isLastPage = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val lastPosition = layoutManager.findLastVisibleItemPosition()
        if (lastPosition == layoutManager.itemCount - 1 || lastPosition == 0) {
            onLoadMore(++curPage)
        }
    }

    protected abstract fun onLoadMore(page: Int)
}