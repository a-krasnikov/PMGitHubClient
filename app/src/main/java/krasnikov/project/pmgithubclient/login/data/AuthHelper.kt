package krasnikov.project.pmgithubclient.login.data

import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import krasnikov.project.pmgithubclient.login.data.model.AccessToken
import krasnikov.project.pmgithubclient.utils.Result
import java.lang.Exception
import javax.inject.Inject

class AuthHelper @Inject constructor(private val loginService: LoginService) {

    private companion object {
        const val SCHEMA = "https"
        const val HOST = "github.com"
        const val REDIRECT_URL = "pmgithubclient://callback"

        const val CLIENT_ID: String = "7096fdc9944d7e8c5c73"
        const val CLIENT_SECRET: String = "8d8770f3497a794efc419b2084d3034ffeca1464"

        const val OAUTH2_PATH = "login/oauth/authorize"
        const val OAUTH2_SCOPE = "user,repo"
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

    suspend fun getAccessToken(code: String): AccessToken {
        return withContext(Dispatchers.IO) {
            loginService.getAccessToken(CLIENT_ID, CLIENT_SECRET, code)
        }
    }
}