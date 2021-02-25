package krasnikov.project.pmgithubclient.repoinfo.ui.readme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.repoinfo.data.RepositoryService
import krasnikov.project.pmgithubclient.repoinfo.data.model.ReadMe
import krasnikov.project.pmgithubclient.utils.State
import java.lang.Exception

class ReadMeViewModel(
    private val owner: String,
    private val repo: String,
    private val repositoryService: RepositoryService
) : BaseViewModel() {

    private val _content = MutableLiveData<State<ReadMe, Exception>>()
    val content
        get() = _content as LiveData<State<ReadMe, Exception>>

    init {
        loadReadme()
    }

    private fun loadReadme() {
        viewModelScope.launch {
            _content.value = State.Loading
            withContext(Dispatchers.IO) {
                try {
                    _content.postValue(State.Content(repositoryService.getReadMe(owner, repo)))
                } catch (ex: Exception) {
                    //TODO Error
                    _content.postValue(State.Error(ex))
                }
            }
        }
    }
}