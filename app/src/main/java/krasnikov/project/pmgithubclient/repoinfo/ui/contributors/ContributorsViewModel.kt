package krasnikov.project.pmgithubclient.repoinfo.ui.contributors

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.repoinfo.data.RepositoryService
import krasnikov.project.pmgithubclient.repoinfo.data.model.Contributor
import krasnikov.project.pmgithubclient.utils.PagedList
import krasnikov.project.pmgithubclient.utils.Result
import java.lang.Exception

class ContributorsViewModel(
    private val owner: String,
    private val repo: String,
    private val repositoryService: RepositoryService
) : BaseViewModel() {

    val pagedListContributors by lazy {
        object : PagedList<Contributor>() {
            override fun loadNextData(page: Int, callback: (Result<List<Contributor>>) -> Unit) {
                viewModelScope.launch {
                    val result = withContext(Dispatchers.IO) {
                        try {
                            Result.Success(repositoryService.getContributors(owner, repo, page))
                        } catch (ex: Exception) {
                            //TODO Error
                            Result.Error(ex)
                        }
                    }
                    callback(result)
                }
            }
        }
    }
}