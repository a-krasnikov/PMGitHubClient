package krasnikov.project.pmgithubclient.login.ui

import android.content.Intent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import krasnikov.project.pmgithubclient.app.data.exception.NetworkRequestException
import krasnikov.project.pmgithubclient.app.data.pref.SharedPref
import krasnikov.project.pmgithubclient.app.navigation.NavigationEvent
import krasnikov.project.pmgithubclient.app.navigation.Navigator
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.login.data.AuthHelper
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile
import krasnikov.project.pmgithubclient.utils.ErrorType
import krasnikov.project.pmgithubclient.utils.State
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authHelper: AuthHelper,
    private val pref: SharedPref
) : BaseViewModel() {

    private val _content = MutableStateFlow<State<Unit, ErrorType>>(State.Loading)
    val content = _content.asStateFlow()

    val authGitHubUrl
        get() = authHelper.authGitHubUrl

    fun handleOauth(intent: Intent) {
        intent.data?.let { it ->
            authHelper.getCodeFromUri(it)?.let { code ->
                getAccessToken(code)
            }
        }
    }

    private fun navigateToUserInfo() {
        _navigationEvent.postValue(NavigationEvent {
            Navigator.navigateToUserInfo(
                it,
                UserProfile.LoggedUser
            )
        })
    }

    private fun getAccessToken(code: String) {
        baseViewModelScope.launch() {
            _content.value = State.Loading
            val result = authHelper.getAccessToken(code)
            pref.token = "${result.tokenType} ${result.accessToken}"
            _content.value = State.Content(Unit)
            navigateToUserInfo()
        }
    }

    override fun handleError(throwable: Throwable, coroutineName: CoroutineName?) {
        super.handleError(throwable, coroutineName)
        when (throwable) {
            is NetworkRequestException -> {
                _content.value = State.Error(ErrorType.AccessTokenError)
            }

            else -> {
                _content.value = State.Error(ErrorType.UnknownError)
            }
        }
    }
}
