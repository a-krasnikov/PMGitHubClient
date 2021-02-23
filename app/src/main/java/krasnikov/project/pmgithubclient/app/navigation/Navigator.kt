package krasnikov.project.pmgithubclient.app.navigation

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.login.ui.LoginFragment
import krasnikov.project.pmgithubclient.repoinfo.ui.RepoInfoFragment
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile
import krasnikov.project.pmgithubclient.userinfo.ui.UserInfoFragment

object Navigator {
    fun navigateToLogin(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            add<LoginFragment>(R.id.fragment_container)
            setReorderingAllowed(true)
        }
    }

    fun navigateToRepoInfo(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            replace<RepoInfoFragment>(R.id.fragment_container)
        }
    }

    fun navigateToUserInfo(fragmentManager: FragmentManager, userProfile: UserProfile) {
        fragmentManager.commit {
            val fragment = UserInfoFragment.newInstance(userProfile)
            replace(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
        }
    }
}