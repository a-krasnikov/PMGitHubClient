package krasnikov.project.pmgithubclient.repoinfo.ui.contributors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.repoinfo.data.RepositoryService
import krasnikov.project.pmgithubclient.utils.State
import java.lang.Exception

class ContributorsViewModel(private val repositoryService: RepositoryService) : BaseViewModel() {

    private val _content = MutableLiveData<State<List<User>, Exception>>()
    val content
        get() = _content as LiveData<State<List<User>, Exception>>

    init {
        loadContributors(1)
    }

    fun loadContributors(page: Int) {
        viewModelScope.launch {
            _content.value = State.Loading
            withContext(Dispatchers.IO) {
                try {
                    _content.postValue(
                        State.Content(
                            repositoryService.getContributors("freeCodeCamp", "freeCodeCamp", page)
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