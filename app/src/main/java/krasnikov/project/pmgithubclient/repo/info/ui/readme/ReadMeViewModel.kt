package krasnikov.project.pmgithubclient.repo.info.ui.readme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.repo.info.data.RepositoryService
import krasnikov.project.pmgithubclient.repo.info.data.model.ReadMe
import krasnikov.project.pmgithubclient.utils.State
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ReadMeViewModel @Inject constructor(
    private val repositoryService: RepositoryService
) : BaseViewModel() {

    private val _content = MutableLiveData<State<ReadMe, Exception>>()
    val content
        get() = _content as LiveData<State<ReadMe, Exception>>

    fun loadReadme(owner: String, repo: String) {
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

    override fun handleError(throwable: Throwable, coroutineName: CoroutineName?) {
        TODO("Not yet implemented")
    }
}