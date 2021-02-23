package krasnikov.project.pmgithubclient.userinfo.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import krasnikov.project.pmgithubclient.app.data.AuthInterceptor
import krasnikov.project.pmgithubclient.app.data.ErrorInterceptor
import krasnikov.project.pmgithubclient.app.data.pref.SharedPref
import krasnikov.project.pmgithubclient.userinfo.data.model.Repo
import krasnikov.project.pmgithubclient.userinfo.data.model.User
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile
import krasnikov.project.pmgithubclient.utils.Result
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.Exception

class UserInfoRepository(private val context: Context/*private val userService: UserService*/) {

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
                                .addInterceptor(AuthInterceptor(SharedPref(context)))
                                .addInterceptor(ErrorInterceptor())
                                .build()
                )
                .baseUrl(HttpUrl.Builder().scheme(SCHEMA).host(HOST).build())
                .addConverterFactory(converterFactory)
                .build()
    }

    private val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    suspend fun getUser(userProfile: UserProfile): Result<User> {
        return withContext(Dispatchers.IO) {
            try {
                when (userProfile) {
                    is UserProfile.LoggedUser -> {
                        Result.Success(userService.getLoggedUser())
                    }
                    is UserProfile.User -> {
                        Result.Success(userService.getUser(userProfile.username))
                    }
                }
            } catch (ex: Exception) {
                Result.Error(ex)
            }
        }
    }

    suspend fun getUserRepos(userProfile: UserProfile, page: Int): Result<List<Repo>> {
        return withContext(Dispatchers.IO) {
            try {
                when (userProfile) {
                    is UserProfile.LoggedUser -> {
                        Result.Success(userService.getLoggedUserRepos(page))
                    }
                    is UserProfile.User -> {
                        Result.Success(userService.getUserRepos(userProfile.username, page))
                    }
                }
            } catch (ex: Exception) {
                Result.Error(ex)
            }
        }
    }
}