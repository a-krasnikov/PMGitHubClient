package krasnikov.project.pmgithubclient.repo.info.ui.issues

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.repo.info.data.RepositoryService
import krasnikov.project.pmgithubclient.repo.info.data.model.Issue
import krasnikov.project.pmgithubclient.utils.PagedList
import krasnikov.project.pmgithubclient.utils.Result
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class IssuesViewModel @Inject constructor(
    private val repositoryService: RepositoryService
) : BaseViewModel() {

    fun loadIssues(owner: String, repo: String) =
        object : PagedList<Issue>() {
            override fun loadNextData(page: Int, callback: (Result<List<Issue>>) -> Unit) {
                viewModelScope.launch {
                    val result = withContext(Dispatchers.IO) {
                        try {
                            Result.Success(repositoryService.getIssues(owner, repo, page))
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
