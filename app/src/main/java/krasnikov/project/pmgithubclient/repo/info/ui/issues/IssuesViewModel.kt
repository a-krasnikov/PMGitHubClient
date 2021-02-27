package krasnikov.project.pmgithubclient.repo.info.ui.issues

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.repo.info.data.RepositoryService
import krasnikov.project.pmgithubclient.repo.info.data.model.Issue
import krasnikov.project.pmgithubclient.utils.PagedList

class IssuesViewModel(
    private val owner: String,
    private val repo: String,
    private val repositoryService: RepositoryService
) : BaseViewModel() {

    val pagedListIssue by lazy {
        object : PagedList<Issue>(viewModelScope) {
            override suspend fun loadNextData(page: Int) = withContext(Dispatchers.IO) {
                repositoryService.getIssues(owner, repo, page)
            }
        }
    }
}