package krasnikov.project.pmgithubclient.login.ui

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import krasnikov.project.pmgithubclient.app.data.exception.NetworkRequestException
import krasnikov.project.pmgithubclient.app.data.pref.SharedPref
import krasnikov.project.pmgithubclient.app.navigation.NavigationEvent
import krasnikov.project.pmgithubclient.app.navigation.Navigator
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.login.data.AuthHelper
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile
import krasnikov.project.pmgithubclient.utils.ErrorType
import krasnikov.project.pmgithubclient.utils.Result
import krasnikov.project.pmgithubclient.utils.State
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
        private val authHelper: AuthHelper,
        private val pref: SharedPref
) : BaseViewModel() {

    private val _content = MutableLiveData<State<Unit, ErrorType>>()
    val content
        get() = _content as LiveData<State<Unit, ErrorType>>

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
        _navigationEvent.value =
                NavigationEvent { Navigator.navigateToUserInfo(it, UserProfile.LoggedUser) }
    }

    private fun getAccessToken(code: String) {
        viewModelScope.launch {
            _content.value = State.Loading
            when (val result = authHelper.getAccessToken(code)) {
                is Result.Success -> {
                    //save token
                    pref.token = "${result.data.tokenType} ${result.data.accessToken}"
                    _content.value = State.Content(Unit)
                    navigateToUserInfo()
                }
                //TODO Error
                is Result.Error -> when (result.exception) {
                    is NetworkRequestException -> {
                        _content.value = State.Error(ErrorType.AccessTokenError)
                    }
                    else -> {
                        _content.value = State.Error(ErrorType.UnknownError)
                    }
                }
            }
        }
    }
}