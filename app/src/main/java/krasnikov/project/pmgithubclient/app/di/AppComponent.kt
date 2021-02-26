package krasnikov.project.pmgithubclient.app.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import krasnikov.project.pmgithubclient.app.data.AuthInterceptor
import krasnikov.project.pmgithubclient.app.data.ErrorInterceptor
import krasnikov.project.pmgithubclient.app.data.pref.SharedPref
import krasnikov.project.pmgithubclient.repoinfo.data.RepositoryService
import krasnikov.project.pmgithubclient.search.data.SearchService
import krasnikov.project.pmgithubclient.userinfo.data.UserService
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

object AppComponent {

    const val SCHEMA = "https"
    const val HOST = "api.github.com"

    val converterFactory: Converter.Factory by lazy {
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

    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    val searchService: SearchService by lazy {
        retrofit.create(SearchService::class.java)
    }
}