package krasnikov.project.pmgithubclient.userinfo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import krasnikov.project.pmgithubclient.app.data.exception.NetworkRequestException
import krasnikov.project.pmgithubclient.app.data.exception.RequestNotAuthorizedException
import krasnikov.project.pmgithubclient.app.navigation.NavEvent
import krasnikov.project.pmgithubclient.app.navigation.Navigator
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.userinfo.data.UserInfoRepository
import krasnikov.project.pmgithubclient.userinfo.data.model.Repo
import krasnikov.project.pmgithubclient.userinfo.data.model.User
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile
import krasnikov.project.pmgithubclient.utils.ErrorType
import krasnikov.project.pmgithubclient.utils.Result
import krasnikov.project.pmgithubclient.utils.State
import java.lang.Exception

class UserInfoViewModel(
    private val userProfile: UserProfile,
    private val repository: UserInfoRepository
) : BaseViewModel() {

    private val _contentUser = MutableLiveData<State<User, ErrorType>>()
    val contentUser
        get() = _contentUser as LiveData<State<User, ErrorType>>

    private val _contentRepos = MutableLiveData<State<List<Repo>, ErrorType>>()
    val contentRepos
        get() = _contentRepos as LiveData<State<List<Repo>, ErrorType>>

    init {
        loadUserInfo()
        loadUserRepos(1)
    }

    private fun navigateToLogin() {
        _navigationEvent.value = NavEvent {
            Navigator.navigateToLogin(it)
        }
    }


    private fun loadUserInfo() {
        viewModelScope.launch {
            _contentUser.value = State.Loading
            when (val result = repository.getUser(userProfile)) {
                is Result.Success -> {
                    _contentUser.value = State.Content(result.data)
                }
                is Result.Error -> {
                    when (result.exception) {
                        is RequestNotAuthorizedException -> {
                            navigateToLogin()
                        }
                        is NetworkRequestException -> {
                            _contentUser.value = State.Error(ErrorType.UserNotLoadedError)
                        }
                        else -> {
                            _contentUser.value = State.Error(ErrorType.UnknownError)
                        }
                    }
                }
            }
        }
    }

    fun loadUserRepos(page: Int) {
        viewModelScope.launch {
            _contentUser.value = State.Loading
            when (val result = repository.getUserRepos(userProfile, page)) {
                is Result.Success -> {
                    _contentRepos.value = State.Content(result.data)
                }
                is Result.Error -> {
                    when (result.exception) {
                        is RequestNotAuthorizedException -> {
                            navigateToLogin()
                        }
                        is NetworkRequestException -> {
                            _contentRepos.value = State.Error(ErrorType.ReposNotLoadedError)
                        }
                        else -> {
                            _contentRepos.value = State.Error(ErrorType.UnknownError)
                        }
                    }
                }
            }
        }
    }
}