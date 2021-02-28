package krasnikov.project.pmgithubclient.userinfo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import krasnikov.project.pmgithubclient.app.data.exception.NetworkRequestException
import krasnikov.project.pmgithubclient.app.data.exception.RequestNotAuthorizedException
import krasnikov.project.pmgithubclient.app.navigation.NavigationEvent
import krasnikov.project.pmgithubclient.app.navigation.Navigator
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.userinfo.data.UserInfoRepository
import krasnikov.project.pmgithubclient.userinfo.data.model.Repo
import krasnikov.project.pmgithubclient.userinfo.data.model.User
import krasnikov.project.pmgithubclient.userinfo.data.model.UserInfoModel
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile
import krasnikov.project.pmgithubclient.utils.ErrorType
import krasnikov.project.pmgithubclient.utils.PagedList
import krasnikov.project.pmgithubclient.utils.Result
import krasnikov.project.pmgithubclient.utils.State
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val repository: UserInfoRepository
) : BaseViewModel() {

    private val _contentUser = MutableLiveData<State<UserInfoModel, ErrorType>>()
    val contentUser
        get() = _contentUser as LiveData<State<UserInfoModel, ErrorType>>

    fun loadUserInfo(userProfile: UserProfile) {
        baseViewModelScope.launch(CoroutineName(userCoroutineName)) {
            _contentUser.postValue(State.Loading)
            val userResult = repository.getUser(userProfile)
            _contentUser.postValue(State.Content(UserInfoModel(userResult, loadRepos(userProfile))))

        }
    }

    private fun loadRepos(userProfile: UserProfile) = object : PagedList<Repo>(baseViewModelScope) {
        override suspend fun loadNextData(page: Int) = repository.getUserRepos(userProfile, page)
    }

    fun onRepoClick(repo: String, owner: String) {
        _navigationEvent.value = NavigationEvent {
            Navigator.navigateToRepoInfo(it, owner, repo)
        }
    }

    fun navigateToSearch(query: String) {
        _navigationEvent.value = NavigationEvent {
            Navigator.navigateToSearch(it, query)
        }
    }

    private fun navigateToLogin() {
        _navigationEvent.value = NavigationEvent {
            Navigator.navigateToLogin(it)
        }
    }

    override fun handleError(throwable: Throwable, coroutineName: CoroutineName?) {
        super.handleError(throwable, coroutineName)

        when (throwable) {
            is NetworkRequestException -> {
                if (coroutineName.toString() == userCoroutineName) {
                    _contentUser.postValue(State.Error(ErrorType.UserNotLoadedError))
                } else {
                    _contentUser.postValue(State.Error(ErrorType.ReposNotLoadedError))
                }
            }
            else -> {
                _contentUser.postValue(State.Error(ErrorType.UnknownError))
            }
        }
    }

    private companion object {
        const val userCoroutineName = "user_coroutine"
    }
}