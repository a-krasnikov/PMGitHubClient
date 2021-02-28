package krasnikov.project.pmgithubclient.repo.info.ui.issues

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import krasnikov.project.pmgithubclient.app.navigation.NavigationEvent
import krasnikov.project.pmgithubclient.app.navigation.Navigator
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.repo.info.data.RepositoryService
import krasnikov.project.pmgithubclient.repo.info.data.model.Issue
import krasnikov.project.pmgithubclient.utils.PagedList
import javax.inject.Inject

@HiltViewModel
class IssuesViewModel @Inject constructor(
    private val repositoryService: RepositoryService
) : BaseViewModel() {

    fun loadIssues(owner: String, repo: String) =
        object : PagedList<Issue>(viewModelScope) {
            override suspend fun loadNextData(page: Int) = withContext(Dispatchers.IO) {
                repositoryService.getIssues(owner, repo, page)
            }
        }

    fun navigateToIssueInfo(owner: String, repo: String, issue: Issue) {
        _navigationEvent.value =
            NavigationEvent { Navigator.navigateToIssueInfo(it, owner, repo, issue) }
    }

    override fun handleError(throwable: Throwable, coroutineName: CoroutineName?) {
        TODO("Not yet implemented")
    }
}
