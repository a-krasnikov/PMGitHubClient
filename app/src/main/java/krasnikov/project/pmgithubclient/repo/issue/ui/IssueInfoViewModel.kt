package krasnikov.project.pmgithubclient.repo.issue.ui


import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.repo.issue.data.IssueService
import krasnikov.project.pmgithubclient.repo.issue.data.model.Comment
import krasnikov.project.pmgithubclient.repo.issue.data.model.Reaction
import krasnikov.project.pmgithubclient.repo.issue.data.model.ReactionType
import krasnikov.project.pmgithubclient.repo.info.data.model.Issue
import krasnikov.project.pmgithubclient.utils.PagedList
import javax.inject.Inject

@HiltViewModel
class IssueInfoViewModel @Inject constructor(
    private val issueService: IssueService
) : BaseViewModel() {

    fun getIssueComments(owner: String, repo: String, issue: Issue) =
        object : PagedList<Comment>(viewModelScope) {
            override suspend fun loadNextData(page: Int) = issueService.getIssueComments(owner, repo, issue.number, page)
        }

    suspend fun getCommentReactions(owner: String, repo: String, commentId: Int): List<Reaction> {
        return withContext(Dispatchers.IO) {
            issueService.getIssueCommentReactions(owner, repo, commentId)
        }
    }

    suspend fun createCommentReaction(
        owner: String,
        repo: String,
        commentId: Int,
        reaction: Reaction
    ): List<Reaction> {
        withContext(Dispatchers.IO) {
            issueService.createIssueCommentReaction(owner, repo, commentId, reaction)
        }
        return getCommentReactions(owner, repo, commentId)
    }
}