package krasnikov.project.pmgithubclient.utils

abstract class PagedList<T> {

    private val internalMutableList = mutableListOf<T>()
    private var currentPage: Int = 1

    val size
        get() = internalMutableList.size

    operator fun get(position: Int): T = internalMutableList[position]

    fun onLoadMore(callback: (Result<Int>) -> Unit) {
        loadNextData(++currentPage) {
            when (it) {
                is Result.Success -> {
                    internalMutableList.addAll(it.data)
                    callback(Result.Success(it.data.size))
                }
                is Result.Error -> {
                    callback(Result.Error(it.exception))
                }
            }
        }
    }

    abstract fun loadNextData(page: Int, callback: (Result<List<T>>) -> Unit)
}