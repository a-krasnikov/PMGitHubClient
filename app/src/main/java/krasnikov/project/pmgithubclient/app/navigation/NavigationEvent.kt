package krasnikov.project.pmgithubclient.app.navigation

import androidx.fragment.app.FragmentManager

fun interface NavigationEvent {
    fun navigate(fragmentManager: FragmentManager)
}