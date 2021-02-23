package krasnikov.project.pmgithubclient.utils

sealed class State<out T, out E> {
    object Loading : State<Nothing, Nothing>()
    data class Content<T>(val data: T) : State<T, Nothing>()
    data class Error<E>(val error: E) : State<Nothing, E>()
}