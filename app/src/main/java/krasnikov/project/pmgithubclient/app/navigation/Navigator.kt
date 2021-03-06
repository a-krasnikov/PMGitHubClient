package krasnikov.project.pmgithubclient.app.navigation

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.repo.issue.ui.IssueInfoFragment
import krasnikov.project.pmgithubclient.login.ui.LoginFragment
import krasnikov.project.pmgithubclient.search.ui.SearchFragment
import krasnikov.project.pmgithubclient.repo.info.data.model.Issue
import krasnikov.project.pmgithubclient.repo.info.ui.RepoInfoFragment
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile
import krasnikov.project.pmgithubclient.userinfo.ui.UserInfoFragment

object Navigator {

    fun navigateToLogin(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            replace<LoginFragment>(R.id.fragment_container)
            setReorderingAllowed(true)
        }
    }

    fun startScreen(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            val fragment = UserInfoFragment.newInstance(UserProfile.LoggedUser)
            add(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
        }
    }

    fun navigateToUserInfo(fragmentManager: FragmentManager, userProfile: UserProfile) {
        fragmentManager.commit {
            val fragment = UserInfoFragment.newInstance(userProfile)
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            setReorderingAllowed(true)
        }
    }

    fun navigateToRepoInfo(fragmentManager: FragmentManager, owner: String, repo: String) {
        fragmentManager.commit {
            val fragment = RepoInfoFragment.newInstance(owner, repo)
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            setReorderingAllowed(true)
        }
    }

    fun navigateToSearch(fragmentManager: FragmentManager, query: String) {
        fragmentManager.commit {
            val fragment = SearchFragment.newInstance(query)
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            setReorderingAllowed(true)
        }
    }

    fun navigateToIssueInfo(
        fragmentManager: FragmentManager,
        owner: String,
        repo: String,
        issue: Issue
    ) {
        fragmentManager.commit {
            val fragment = IssueInfoFragment.newInstance(owner, repo, issue)
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            setReorderingAllowed(true)
        }
    }
}