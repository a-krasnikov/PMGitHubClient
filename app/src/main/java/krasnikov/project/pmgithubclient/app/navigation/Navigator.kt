package krasnikov.project.pmgithubclient.app.navigation

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.login.ui.LoginFragment
import krasnikov.project.pmgithubclient.repoinfo.ui.RepoInfoFragment
import krasnikov.project.pmgithubclient.search.ui.SearchFragment
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile
import krasnikov.project.pmgithubclient.userinfo.ui.UserInfoFragment

object Navigator {
    fun navigateToLogin(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            replace<LoginFragment>(R.id.fragment_container)
            setReorderingAllowed(true)
        }
    }

    fun navigateToUserInfo(fragmentManager: FragmentManager, userProfile: UserProfile) {
        fragmentManager.commit {
            val fragment = UserInfoFragment.newInstance(userProfile)
            replace(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
        }
    }

    fun navigateToRepoInfo(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            val fragment =
                RepoInfoFragment.newInstance("android", "architecture-components-samples")
            replace(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
        }
    }

    fun navigateToSearch(fragmentManager: FragmentManager, query: String) {
        fragmentManager.commit {
            val fragment = SearchFragment.newInstance(query)
            replace(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
        }
    }
}