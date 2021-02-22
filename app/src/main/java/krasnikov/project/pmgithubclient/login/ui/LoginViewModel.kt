package krasnikov.project.pmgithubclient.login.ui

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import krasnikov.project.pmgithubclient.app.data.pref.SharedPref
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.login.data.AuthHelper
import krasnikov.project.pmgithubclient.utils.AppMultithreading
import krasnikov.project.pmgithubclient.utils.Result
import krasnikov.project.pmgithubclient.utils.State
import java.lang.Exception

class LoginViewModel(
    private val authHelper: AuthHelper,
    private val pref: SharedPref
) : BaseViewModel() {

    private val _content = MutableLiveData<State<Unit, Exception>>()
    val content
        get() = _content as LiveData<State<Unit, Exception>>

    val authGitHubUrl
        get() = authHelper.authGitHubUrl

    fun handleOauth(intent: Intent) {
        intent.data?.let { it ->
            authHelper.getCodeFromUri(it)?.let { code ->
                getAccessToken(code)
            }
        }
    }

    private fun getAccessToken(code: String) {
        viewModelScope.launch {
            _content.value = State.Loading
            when (val result = authHelper.getAccessToken(code)) {
                is Result.Success -> {
                    //save token
                    pref.token = "${result.data.tokenType} ${result.data.accessToken}"
                    _content.value = State.Content(Unit)
                }
                //TODO Error
                is Result.Error -> _content.value = State.Error(result.exception)
            }
        }
    }
}