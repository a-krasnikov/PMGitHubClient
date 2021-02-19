package krasnikov.project.pmgithubclient.utils

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()

    data class Error(val exception: Exception) : Result<Nothing>()

    fun <R> map(transformation: (T) -> R): Result<R> {
        return when (this) {
            is Success -> Success(transformation(data))
            is Error -> this
        }
    }
}