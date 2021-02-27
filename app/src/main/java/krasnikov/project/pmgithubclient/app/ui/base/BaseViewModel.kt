package krasnikov.project.pmgithubclient.app.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import krasnikov.project.pmgithubclient.app.navigation.NavEvent
import krasnikov.project.pmgithubclient.utils.SingleLiveEvent
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    protected val _navigationEvent = SingleLiveEvent<NavEvent>()
    val navigationEvent
        get() = _navigationEvent as LiveData<NavEvent>
}