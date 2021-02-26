package krasnikov.project.pmgithubclient.app.data

import krasnikov.project.pmgithubclient.app.data.pref.SharedPref
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val pref: SharedPref) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val request = originalRequest.newBuilder()
            //.header("Authorization", pref.token)
            //.header("Accept", "application/vnd.github.v3+json")
            .header("Accept", "application/vnd.github.squirrel-girl-preview+json")
            .build()

        return chain.proceed(request)
    }
}