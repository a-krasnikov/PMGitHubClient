package krasnikov.project.pmgithubclient.utils

import android.os.Handler
import java.lang.Exception
import java.util.concurrent.ExecutorService

class AsyncOperation<T>(
    private val operation: () -> T,
    private val mainHandler: Handler,
    private val executor: ExecutorService
) {

    fun postOnMainThread(callback: (T) -> Unit): CancelableOperation {
        val future = executor.submit {
            try {
                val result = operation()
                if (!Thread.currentThread().isInterrupted) {
                    mainHandler.post {
                        callback(result)
                    }
                }
            } catch (ignore: Exception) {
            }
        }

        return CancelableOperation { future.cancel(true) }
    }

    fun <R> map(transform: (T) -> R): AsyncOperation<R> {
        return AsyncOperation(
            operation = { transform(operation()) },
            mainHandler = mainHandler,
            executor = executor
        )
    }
}

fun interface CancelableOperation {
    fun cancel()
}