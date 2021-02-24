package krasnikov.project.pmgithubclient.repoinfo.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import krasnikov.project.pmgithubclient.app.data.AuthInterceptor
import krasnikov.project.pmgithubclient.app.data.ErrorInterceptor
import krasnikov.project.pmgithubclient.app.data.pref.SharedPref
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

class Test(private val context: Context) {
    private companion object {
        const val SCHEMA = "https"
        const val HOST = "api.github.com"
    }

    private val converterFactory: Converter.Factory by lazy {
        Json { ignoreUnknownKeys = true }
            .asConverterFactory("application/json".toMediaType())
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(
                OkHttpClient().newBuilder()
                    //.addInterceptor(AuthInterceptor(SharedPref(context)))
                    .addInterceptor(ErrorInterceptor())
                    .build()
            )
            .baseUrl(HttpUrl.Builder().scheme(SCHEMA).host(HOST).build())
            .addConverterFactory(converterFactory)
            .build()
    }

    val repositoryService: RepositoryService by lazy {
        retrofit.create(RepositoryService::class.java)
    }
}