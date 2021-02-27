package krasnikov.project.pmgithubclient.app.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import krasnikov.project.pmgithubclient.app.data.AuthInterceptor
import krasnikov.project.pmgithubclient.app.data.ErrorInterceptor
import krasnikov.project.pmgithubclient.app.data.pref.SharedPref
import krasnikov.project.pmgithubclient.search.data.SearchService
import krasnikov.project.pmgithubclient.userinfo.data.UserService
import krasnikov.project.pmgithubclient.repo.issue.data.IssueService
import krasnikov.project.pmgithubclient.repo.info.data.RepositoryService
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

object AppComponent {

    const val SCHEMA = "https"
    const val HOST = "api.github.com"


}