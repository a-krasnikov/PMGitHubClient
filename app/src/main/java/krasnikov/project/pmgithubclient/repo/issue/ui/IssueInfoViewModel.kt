package krasnikov.project.pmgithubclient.repo.issue.ui


import androidx.lifecycle.viewModelScope
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import krasnikov.project.pmgithubclient.app.data.AuthInterceptor
import krasnikov.project.pmgithubclient.app.data.ErrorInterceptor
import krasnikov.project.pmgithubclient.app.data.pref.SharedPref
import krasnikov.project.pmgithubclient.app.di.AppComponent
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.repo.issue.data.IssueService
import krasnikov.project.pmgithubclient.repo.issue.data.model.Comment
import krasnikov.project.pmgithubclient.repo.issue.data.model.Reaction
import krasnikov.project.pmgithubclient.repo.issue.data.model.ReactionType
import krasnikov.project.pmgithubclient.repo.info.data.model.Issue
import krasnikov.project.pmgithubclient.utils.PagedList
import krasnikov.project.pmgithubclient.utils.Result
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class IssueInfoViewModel @Inject constructor(
    private val issueService: IssueService
) : BaseViewModel() {

    fun getIssueComments(owner: String, repo: String, issue: Issue) =
        object : PagedList<Comment>() {
            override fun loadNextData(page: Int, callback: (Result<List<Comment>>) -> Unit) {
                viewModelScope.launch() {
                    val comments = try {
                        Result.Success(withContext(Dispatchers.IO) {
                            issueService.getIssueComments(owner, repo, issue.number, page)
                        })
                    } catch (exception: Exception) {
                        Result.Error(exception)
                    }
                    callback(comments)
                }
            }

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
        reaction: ReactionType
    ): List<Reaction> {
        withContext(Dispatchers.IO) {
            issueService.createIssueCommentReaction(owner, repo, commentId, reaction.content)
        }
        return getCommentReactions(owner, repo, commentId)
    }

}