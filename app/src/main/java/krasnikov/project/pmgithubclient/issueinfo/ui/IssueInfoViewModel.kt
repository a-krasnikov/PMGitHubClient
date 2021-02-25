package krasnikov.project.pmgithubclient.issueinfo.ui


import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.issueinfo.data.IssueService
import krasnikov.project.pmgithubclient.issueinfo.data.model.Comment
import krasnikov.project.pmgithubclient.repoinfo.data.model.Issue
import krasnikov.project.pmgithubclient.utils.PagedList
import krasnikov.project.pmgithubclient.utils.Result
import java.lang.Exception

class IssueInfoViewModel(
        private val owner: String,
        private val repo: String,
        private val issue: Issue,
        private val issueService: IssueService
) : BaseViewModel() {

    val pagedCommentList by lazy {
        object : PagedList<Comment>() {
            override fun loadNextData(page: Int, callback: (Result<List<Comment>>) -> Unit) {
                viewModelScope.launch() {
                    val comments = try {
                        Result.Success(getIssueComments(page))
                    } catch (exception: Exception) {
                        Result.Error(exception)
                    }
                    callback(comments)
                }
            }

        }
    }

    suspend fun getIssueComments(page: Int): List<Comment> {
        return withContext(Dispatchers.IO) {
            issueService.getIssueComments(owner, repo, issue.number, page)
        }
    }
}