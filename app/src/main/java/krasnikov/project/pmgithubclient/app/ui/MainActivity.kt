package krasnikov.project.pmgithubclient.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.app.data.pref.SharedPref
import krasnikov.project.pmgithubclient.app.navigation.Navigator
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val eventChannel = Channel<String>(Channel.CONFLATED)
    val eventFlow = eventChannel.receiveAsFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (SharedPref(this).token.isEmpty()) {
            Navigator.navigateToLogin(supportFragmentManager)
        } else {
            Navigator.startScreen(supportFragmentManager)
        }



        eventFlow.onEach {
            println(it)
        }.observeInLifecycle(this)
    }

    override fun onResume() {
        super.onResume()

        GlobalScope.launch {
            repeat(30) {
                eventChannel.send("$it")
                delay(2000)
            }
        }
    }
}


class FlowObserver<T>(
    lifecycleOwner: LifecycleOwner,
    private val flow: Flow<T>,
    private val collector: suspend (T) -> Unit
) {

    private var job: Job? = null

    init {
        lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { source: LifecycleOwner, event: Lifecycle.Event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    job = source.lifecycleScope.launch {
                        flow.collect { collector(it) }
                    }
                }
                Lifecycle.Event.ON_STOP -> {
                    job?.cancel()
                    job = null
                }
                else -> {
                }
            }
        })
    }
}


inline fun <reified T> Flow<T>.observeOnLifecycle(
    lifecycleOwner: LifecycleOwner,
    noinline collector: suspend (T) -> Unit
) = FlowObserver(lifecycleOwner, this, collector)

inline fun <reified T> Flow<T>.observeInLifecycle(
    lifecycleOwner: LifecycleOwner
) = FlowObserver(lifecycleOwner, this, {})
