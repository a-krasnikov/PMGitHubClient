package krasnikov.project.pmgithubclient.userinfo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import krasnikov.project.pmgithubclient.app.data.exception.NetworkRequestException
import krasnikov.project.pmgithubclient.app.data.exception.RequestNotAuthorizedException
import krasnikov.project.pmgithubclient.app.navigation.NavEvent
import krasnikov.project.pmgithubclient.app.navigation.Navigator
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.userinfo.data.UserInfoRepository
import krasnikov.project.pmgithubclient.userinfo.data.model.Repo
import krasnikov.project.pmgithubclient.userinfo.data.model.UserInfoModel
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile
import krasnikov.project.pmgithubclient.utils.ErrorType
import krasnikov.project.pmgithubclient.utils.PagedList
import krasnikov.project.pmgithubclient.utils.Result
import krasnikov.project.pmgithubclient.utils.State

class UserInfoViewModel(
    private val userProfile: UserProfile,
    private val repository: UserInfoRepository
) : BaseViewModel() {

    private val _contentUser = MutableLiveData<State<UserInfoModel, ErrorType>>()
    val contentUser
        get() = _contentUser as LiveData<State<UserInfoModel, ErrorType>>

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {

        viewModelScope.launch {
            _contentUser.value = State.Loading
            when (val userResult = repository.getUser(userProfile)) {
                is Result.Success -> {
                    _contentUser.value =
                        State.Content(UserInfoModel(userResult.data, loadRepos()))
                }
                is Result.Error -> {
                    when (userResult.exception) {
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

    private fun loadRepos() = object : PagedList<Repo>(viewModelScope) {
        override suspend fun loadNextData(page: Int) = repository.getUserRepos(userProfile, page)
    }

    fun onRepoClick(repo: Repo) {
        _navigationEvent.value = NavEvent {
            Navigator.navigateToRepoInfo(it)
        }
    }

    private fun navigateToLogin() {
        _navigationEvent.value = NavEvent {
            Navigator.navigateToLogin(it)
        }
    }
}