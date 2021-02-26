package krasnikov.project.pmgithubclient.app.data

import krasnikov.project.pmgithubclient.app.data.exception.NetworkRequestException
import krasnikov.project.pmgithubclient.app.data.exception.RequestNotAuthorizedException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ErrorInterceptor @Inject constructor() : Interceptor {
    private companion object {
        const val HTTP_NOT_AUTHORIZED = 401
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if(response.isSuccessful) {
            return response
        }

        when(response.code) {
            HTTP_NOT_AUTHORIZED -> {
                throw RequestNotAuthorizedException()
            }
            else -> {
                throw NetworkRequestException()
            }
        }

    }
}