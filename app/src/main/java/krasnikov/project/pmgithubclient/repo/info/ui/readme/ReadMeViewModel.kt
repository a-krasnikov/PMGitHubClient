package krasnikov.project.pmgithubclient.repo.info.ui.readme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import krasnikov.project.pmgithubclient.app.data.exception.NetworkRequestException
import krasnikov.project.pmgithubclient.app.ui.base.BaseViewModel
import krasnikov.project.pmgithubclient.repo.info.data.RepositoryService
import krasnikov.project.pmgithubclient.repo.info.data.model.ReadMe
import krasnikov.project.pmgithubclient.utils.ErrorType
import krasnikov.project.pmgithubclient.utils.State
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ReadMeViewModel @Inject constructor(
    private val repositoryService: RepositoryService
) : BaseViewModel() {

    private val _content = MutableLiveData<State<ReadMe, ErrorType>>()
    val content
        get() = _content as LiveData<State<ReadMe, ErrorType>>

    fun loadReadme(owner: String, repo: String) {
        baseViewModelScope.launch {
            _content.postValue(State.Loading)
            withContext(Dispatchers.IO) {
                _content.postValue(State.Content(repositoryService.getReadMe(owner, repo)))
            }
        }
    }

    override fun handleError(throwable: Throwable, coroutineName: CoroutineName?) {
        super.handleError(throwable, coroutineName)
        when(throwable) {
            is NetworkRequestException -> {
                _content.postValue(State.Error(ErrorType.NoReadMeError))
            }
            else ->  {
                _content.postValue(State.Error(ErrorType.UnknownError))
            }
        }
    }
}