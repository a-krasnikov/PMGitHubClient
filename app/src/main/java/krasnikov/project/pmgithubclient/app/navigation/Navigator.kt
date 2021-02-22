package krasnikov.project.pmgithubclient.app.navigation

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.login.ui.LoginFragment

object Navigator {

    fun navigateToLogin(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            add<LoginFragment>(R.id.fragment_container)
            setReorderingAllowed(true)
        }
    }
}