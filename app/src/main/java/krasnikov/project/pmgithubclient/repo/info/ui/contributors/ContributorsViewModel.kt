package krasnikov.project.pmgithubclient.repo.info.ui.contributors

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun loadContributors(owner: String, repo: String) =
        object : PagedList<Contributor>(viewModelScope) {
            override suspend fun loadNextData(page: Int) = withContext(Dispatchers.IO) {
                repositoryService.getContributors(owner, repo, page)
            }
        }

    fun navigateToUserInfo(login: String) {
        _navigationEvent.value =
            NavigationEvent { Navigator.navigateToUserInfo(it, UserProfile.User(login)) }
    }
}
