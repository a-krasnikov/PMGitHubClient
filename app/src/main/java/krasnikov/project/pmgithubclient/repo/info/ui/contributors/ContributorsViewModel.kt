package krasnikov.project.pmgithubclient.repo.info.ui.contributors

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.repo.info.data.RepositoryService
import krasnikov.project.pmgithubclient.repo.info.data.model.Contributor
import krasnikov.project.pmgithubclient.utils.PagedList

class ContributorsViewModel(
    private val owner: String,
    private val repo: String,
    private val repositoryService: RepositoryService
) : BaseViewModel() {

    val pagedListContributors by lazy {
        object : PagedList<Contributor>(viewModelScope) {
            override suspend fun loadNextData(page: Int) = withContext(Dispatchers.IO) {
                repositoryService.getContributors(owner, repo, page)
            }
        }
    }
}
