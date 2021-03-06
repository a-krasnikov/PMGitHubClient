package krasnikov.project.pmgithubclient.repo.info.ui.contributors

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import krasnikov.project.pmgithubclient.app.navigation.NavigationEvent
import krasnikov.project.pmgithubclient.app.navigation.Navigator
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.repo.info.data.RepositoryService
import krasnikov.project.pmgithubclient.repo.info.data.model.Contributor
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile
import krasnikov.project.pmgithubclient.utils.PagedList
import javax.inject.Inject

@HiltViewModel
class ContributorsViewModel @Inject constructor(
    private val repositoryService: RepositoryService
) : BaseViewModel() {

    private lateinit var _contributors: PagedList<Contributor>

    fun loadContributors(owner: String, repo: String): PagedList<Contributor> {
        _contributors = object : PagedList<Contributor>(baseViewModelScope) {
            override suspend fun loadNextData(page: Int) = withContext(Dispatchers.IO) {
                repositoryService.getContributors(owner, repo, page)
            }
        }

        return _contributors
    }


    fun navigateToUserInfo(login: String) {
        _navigationEvent.value =
            NavigationEvent { Navigator.navigateToUserInfo(it, UserProfile.User(login)) }
    }

    override fun handleError(throwable: Throwable, coroutineName: CoroutineName?) {
        super.handleError(throwable, coroutineName)
        _contributors.notifyError(Exception(throwable))
    }
}
