package krasnikov.project.pmgithubclient.app.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import krasnikov.project.pmgithubclient.app.data.AuthInterceptor
import krasnikov.project.pmgithubclient.app.data.ErrorInterceptor
import krasnikov.project.pmgithubclient.app.data.pref.SharedPref
import krasnikov.project.pmgithubclient.login.data.LoginService
import krasnikov.project.pmgithubclient.repo.info.data.RepositoryService
import krasnikov.project.pmgithubclient.repo.issue.data.IssueService
import krasnikov.project.pmgithubclient.search.data.SearchService
import krasnikov.project.pmgithubclient.userinfo.data.UserService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    companion object {
        const val API_BASE_URL = "https://api.github.com"
        const val OAUTH_BASE_URL = "https://github.com"
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return Json { ignoreUnknownKeys = true; coerceInputValues = true }
            .asConverterFactory("application/json".toMediaType())
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(API_BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
    }


    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        errorInterceptor: ErrorInterceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .addInterceptor(errorInterceptor)
            .build()
    }

    @Provides
    fun provideAuthInterceptor(sharedPref: SharedPref): AuthInterceptor {
        return AuthInterceptor(sharedPref)
    }

    @Provides
    fun provideRepositoryService(retrofit: Retrofit): RepositoryService {
        return retrofit.create(RepositoryService::class.java)
    }

    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    fun provideSearchService(retrofit: Retrofit): SearchService {
        return retrofit.create(SearchService::class.java)
    }

    @Provides
    fun provideIssueService(retrofit: Retrofit): IssueService {
        return retrofit.create(IssueService::class.java)
    }

    @Provides
    fun provideLoginService(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): LoginService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(OAUTH_BASE_URL)
            .addConverterFactory(converterFactory)
            .build().create(LoginService::class.java)
    }
}