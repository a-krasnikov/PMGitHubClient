package krasnikov.project.pmgithubclient.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import krasnikov.project.pmgithubclient.app.navigation.NavigationEvent
import krasnikov.project.pmgithubclient.app.navigation.Navigator
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.search.data.SearchService
import krasnikov.project.pmgithubclient.userinfo.data.model.User
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile
import krasnikov.project.pmgithubclient.utils.PagedList
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchService: SearchService) :
    BaseViewModel() {

    private val _contentSearch =
        MutableLiveData<PaginationData<User>>(PaginationData(emptyList(), PaginationState.Loading))
    val contentSearch
        get() = _contentSearch as LiveData<PaginationData<User>>

    fun searchUser(query: String) {
        loadNextData(1)
    }

    fun loadNextData(page: Int) {
        println("page $page")
        _contentSearch.value = _contentSearch.value?.stateLoading()
        baseViewModelScope.launch(Dispatchers.IO) {
            _contentSearch.postValue(
                _contentSearch.value?.stateComplete(
                    searchService.searchUsers(
                        "vova",
                        page
                    ).items
                )
            )
        }
    }

    fun navigateToUserInfo(login: String) {
        _navigationEvent.value =
            NavigationEvent { Navigator.navigateToUserInfo(it, UserProfile.User(login)) }
    }

    override fun handleError(throwable: Throwable, coroutineName: CoroutineName?) {
        super.handleError(throwable, coroutineName)
        //_contentSearch.value?.notifyError(Exception(throwable))
    }
}
