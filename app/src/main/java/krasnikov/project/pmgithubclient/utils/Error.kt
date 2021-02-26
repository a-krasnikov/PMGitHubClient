package krasnikov.project.pmgithubclient.utils

import androidx.annotation.StringRes

enum class ErrorType(@StringRes val stringRes: Int = 0) {
    NetworkError,
    IssueCommentsError,
    AccessTokenError,
    ReposNotLoadedError,
    UserNotLoadedError,
    UnknownError
}