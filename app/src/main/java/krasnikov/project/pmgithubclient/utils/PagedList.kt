package krasnikov.project.pmgithubclient.utils

import kotlinx.coroutines.*

abstract class PagedList<T>(private val coroutineScope: CoroutineScope) {

    private val set = mutableSetOf<T>()
    private var nextPage: Int = 1
    private var canLoadMore = true
    private var loadJob: Job? = null

    val size
        get() = set.size

    var stateLoadedListener: (State<List<T>, Exception>) -> Unit = {}

    operator fun get(position: Int): T = set.elementAt(position)

    private var i = 0

    fun onLoadMore() {
        if (loadJob?.isActive == true) return

        if (canLoadMore) {
            loadJob = coroutineScope.launch(CoroutineName("PagedListCoroutine")) {
                val newData = loadNextData(nextPage)
                set.addAll(newData)
                nextPage++
                canLoadMore = newData.isNotEmpty()
                stateLoadedListener(State.Content(set.toList()))
            }
        }
    }

    fun notifyError(ex: Exception) {
        stateLoadedListener(State.Error(ex))
    }

    abstract suspend fun loadNextData(page: Int): List<T>
}