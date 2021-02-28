package krasnikov.project.pmgithubclient.app.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import krasnikov.project.pmgithubclient.app.data.exception.NetworkRequestException
import krasnikov.project.pmgithubclient.app.data.exception.RequestNotAuthorizedException
import krasnikov.project.pmgithubclient.app.navigation.NavigationEvent
import krasnikov.project.pmgithubclient.app.navigation.Navigator
import krasnikov.project.pmgithubclient.utils.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
        handleError(throwable, context[CoroutineName.Key])
    }
    protected val baseViewModelScope = CoroutineScope(SupervisorJob() + exceptionHandler)

    open fun handleError(throwable: Throwable, coroutineName: CoroutineName?) {
        if(throwable is RequestNotAuthorizedException) {
            _navigationEvent.postValue( NavigationEvent { Navigator.navigateToLogin(it) })
        }
    }

    protected val _navigationEvent = SingleLiveEvent<NavigationEvent>()
    val navigationEvent
        get() = _navigationEvent as LiveData<NavigationEvent>


    override fun onCleared() {
        baseViewModelScope.cancel()
        super.onCleared()
    }
}