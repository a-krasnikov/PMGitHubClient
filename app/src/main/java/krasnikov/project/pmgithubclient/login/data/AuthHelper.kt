package krasnikov.project.pmgithubclient.login.data

import android.net.Uri
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import krasnikov.project.pmgithubclient.app.data.ErrorInterceptor
import krasnikov.project.pmgithubclient.login.data.model.AccessToken
import krasnikov.project.pmgithubclient.utils.Result
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.Exception

class AuthHelper {

    private companion object {
        const val SCHEMA = "https"
        const val HOST = "github.com"
        const val REDIRECT_URL = "pmgithubclient://callback"

        const val CLIENT_ID: String = "Iv1.ed322da1df41ca03"
        const val CLIENT_SECRET: String = "52fde02da40698db85c852b8ac63db51d7687fdb"

        const val OAUTH2_PATH = "login/oauth/authorize"
        const val OAUTH2_SCOPE = "user,repo"
    }

    private val converterFactory: Converter.Factory by lazy {
        Json { ignoreUnknownKeys = true }
            .asConverterFactory("application/json".toMediaType())
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(ErrorInterceptor())
                    .build()
            )
            .baseUrl(HttpUrl.Builder().scheme(SCHEMA).host(HOST).build())
            .addConverterFactory(converterFactory)
            .build()
    }

    private val loginService: LoginService by lazy {
        retrofit.create(LoginService::class.java)
    }

    val authGitHubUrl: Uri
        get() = Uri.Builder()
            .scheme(SCHEMA)
            .authority(HOST)
            .appendEncodedPath(OAUTH2_PATH)
            .appendQueryParameter("client_id", CLIENT_ID)
            .appendQueryParameter("scope", OAUTH2_SCOPE)
            .appendQueryParameter("redirect_url", REDIRECT_URL)
            .build()

    fun getCodeFromUri(uri: Uri?): String? {
        uri ?: return null
        if (!uri.toString().startsWith(REDIRECT_URL)) {
            return null
        }
        return uri.getQueryParameter("code")
    }

    suspend fun getAccessToken(code: String): Result<AccessToken> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Success(loginService.getAccessToken(CLIENT_ID, CLIENT_SECRET, code))
            } catch (ex: Exception) {
                Result.Error(ex)
            }
        }
    }
}