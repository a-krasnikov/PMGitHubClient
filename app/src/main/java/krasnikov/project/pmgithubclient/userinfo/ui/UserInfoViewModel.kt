package krasnikov.project.pmgithubclient.userinfo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.userinfo.data.UserInfoRepository
import krasnikov.project.pmgithubclient.userinfo.data.model.Repo
import krasnikov.project.pmgithubclient.userinfo.data.model.User
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile
import krasnikov.project.pmgithubclient.utils.Result
import krasnikov.project.pmgithubclient.utils.State
import java.lang.Exception

class UserInfoViewModel(
    private val userProfile: UserProfile,
    private val repository: UserInfoRepository
) : BaseViewModel() {

    private val _contentUser = MutableLiveData<State<User, Exception>>()
    val contentUser
        get() = _contentUser as LiveData<State<User, Exception>>

    private val _contentRepos = MutableLiveData<State<List<Repo>, Exception>>()
    val contentRepos
        get() = _contentRepos as LiveData<State<List<Repo>, Exception>>

    init {
        loadUserInfo()
        loadUserRepos(1)
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            _contentUser.value = State.Loading
            when (val result = repository.getUser(userProfile)) {
                is Result.Success -> {
                    _contentUser.value = State.Content(result.data)
                }
                is Result.Error -> {
                    _contentUser.value = State.Error(result.exception)
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
                    _contentRepos.value = State.Error(result.exception)
                }
            }
        }
    }
}