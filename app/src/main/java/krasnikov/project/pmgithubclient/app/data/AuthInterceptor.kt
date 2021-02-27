package krasnikov.project.pmgithubclient.app.data

import krasnikov.project.pmgithubclient.app.data.pref.SharedPref
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val sharedPref: SharedPref) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val request = originalRequest.newBuilder()
            .addHeader("Authorization", sharedPref.token)
            .addHeader("Accept", "application/vnd.github.v3+json")
            //.header("Accept", "application/vnd.github.squirrel-girl-preview+json")
            .build()

        return chain.proceed(request)
    }
}