package krasnikov.project.pmgithubclient.repo.issue.ui


import androidx.lifecycle.viewModelScope
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
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

class IssueInfoViewModel(
        private val owner: String,
        private val repo: String,
        private val issue: Issue,
        private val issueService: IssueService,
        private val sharedPrefs: SharedPref
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

    suspend fun getCommentReactions(commentId: Int): List<Reaction> {
        return withContext(Dispatchers.IO) {
            service.getIssueCommentReactions(owner, repo, commentId)
        }
    }

    suspend fun createCommentReaction(commentId: Int, reaction: ReactionType): List<Reaction> {
        withContext(Dispatchers.IO) {
            service.createIssueCommentReaction(owner, repo, commentId, reaction.content)
        }
        return getCommentReactions(commentId)
    }

    private val service =
            Retrofit.Builder()
                    .client(
                            OkHttpClient().newBuilder()
                                    .addInterceptor(AuthInterceptor(sharedPrefs))
                                    .addInterceptor(ErrorInterceptor())
                                    .build()
                    )
                    .baseUrl(HttpUrl.Builder().scheme(AppComponent.SCHEMA).host(AppComponent.HOST).build())
                    .addConverterFactory(Json { ignoreUnknownKeys = true }
                            .asConverterFactory("application/json".toMediaType()))
                    .build().create(IssueService::class.java)
}