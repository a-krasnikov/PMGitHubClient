package krasnikov.project.pmgithubclient.app.navigation

import androidx.fragment.app.FragmentManager

fun interface NavEvent {
    fun navigate(fragmentManager: FragmentManager)
}