package krasnikov.project.pmgithubclient.repoinfo.ui.issues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.repoinfo.data.RepositoryService
import krasnikov.project.pmgithubclient.repoinfo.data.model.IssueModel
import krasnikov.project.pmgithubclient.utils.State
import java.lang.Exception

class IssuesViewModel(private val repositoryService: RepositoryService) : BaseViewModel() {

    private val _content = MutableLiveData<State<List<IssueModel>, Exception>>()
    val content
        get() = _content as LiveData<State<List<IssueModel>, Exception>>

    init {
        loadIssues(1)
    }

    fun loadIssues(page: Int) {
        viewModelScope.launch {
            _content.value = State.Loading
            withContext(Dispatchers.IO) {
                try {
                    _content.postValue(
                        State.Content(
                            repositoryService.getIssues("freeCodeCamp", "freeCodeCamp", page)
                        )
                    )
                } catch (ex: Exception) {
                    //TODO Error
                    _content.postValue(State.Error(ex))
                }
            }
        }
    }

}