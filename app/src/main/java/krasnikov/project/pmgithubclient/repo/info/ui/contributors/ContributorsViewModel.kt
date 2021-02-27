package krasnikov.project.pmgithubclient.repo.info.ui.contributors

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.repo.info.data.RepositoryService
import krasnikov.project.pmgithubclient.repo.info.data.model.Contributor
import krasnikov.project.pmgithubclient.utils.PagedList
import krasnikov.project.pmgithubclient.utils.Result
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ContributorsViewModel @Inject constructor(
    private val repositoryService: RepositoryService
) : BaseViewModel() {

    fun loadContributors(owner: String, repo: String) =
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