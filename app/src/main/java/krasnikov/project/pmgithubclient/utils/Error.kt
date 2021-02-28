package krasnikov.project.pmgithubclient.utils

import androidx.annotation.StringRes
import krasnikov.project.pmgithubclient.R

enum class ErrorType(@StringRes val stringRes: Int) {
    NoReadMeError(R.string.error_readme),
    AccessTokenError(R.string.error_access_token),
    ReposNotLoadedError(R.string.error_repos_not_loaded),
    UserNotLoadedError(R.string.error_user_not_loaded),
    UnknownError(R.string.error_unknown)
}