package krasnikov.project.pmgithubclient.login.data

import android.net.Uri
import krasnikov.project.pmgithubclient.login.data.model.AccessToken
import krasnikov.project.pmgithubclient.utils.AppMultithreading
import krasnikov.project.pmgithubclient.utils.AsyncOperation

class AuthHelper(
    private val loginService: LoginService,
    private val multithreading: AppMultithreading
) {

    private companion object {
        const val SCHEMA = "https"
        const val HOST = "github.com"
        const val REDIRECT_URL = "parimatchacademy://callback"

        const val CLIENT_ID: String = "Iv1.9adf0bf789a874bc"
        const val CLIENT_SECRET: String = "c3689fa836cbfde5a0d67a88278d6e8a01412fdd"

        const val OAUTH2_PATH = "login/oauth/authorize"
        const val OAUTH2_SCOPE = "user,repo"
    }

    /* private val retrofit: Retrofit by lazy {
         Retrofit.Builder()
             .client(
                 OkHttpClient().newBuilder()
                     .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                     .build()
             )
             .baseUrl(HttpUrl.Builder().scheme(schema).host(host).build())
             .addConverterFactory(converterFactory)
             .build()
     }

     private val githubService: GitHubService by lazy {
         retrofit.create(GitHubService::class.java)
     }*/

    fun buildAuthGitHubUrl(): Uri {
        return Uri.Builder()
            .scheme(SCHEMA)
            .authority(HOST)
            .appendEncodedPath(OAUTH2_PATH)
            .appendQueryParameter("client_id", CLIENT_ID)
            .appendQueryParameter("scope", OAUTH2_SCOPE)
            .appendQueryParameter("redirect_url", REDIRECT_URL)
            .build()
    }

    fun getCodeFromUri(uri: Uri?): String? {
        uri ?: return null
        if (!uri.toString().startsWith(REDIRECT_URL)) {
            return null
        }
        return uri.getQueryParameter("code")
    }

    fun getAccessToken(code: String): AsyncOperation<AccessToken> {
        return multithreading.async {
            loginService.getAccessToken(CLIENT_ID, CLIENT_SECRET, code)
        }
    }
}