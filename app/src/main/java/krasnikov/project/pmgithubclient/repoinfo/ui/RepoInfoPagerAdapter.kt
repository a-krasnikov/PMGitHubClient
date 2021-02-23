package krasnikov.project.pmgithubclient.repoinfo.ui

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.repoinfo.ui.contributors.ContributorsFragment
import krasnikov.project.pmgithubclient.repoinfo.ui.issues.IssuesFragment
import krasnikov.project.pmgithubclient.repoinfo.ui.readme.ReadMeFragment
import java.lang.IllegalArgumentException

class RepoInfoPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = Page.values().size

    override fun createFragment(position: Int): Fragment {
        return when (Page.values()[position]) {
            Page.README -> {
                ReadMeFragment.newInstance()
            }
            Page.CONTRIBUTORS -> {
                ContributorsFragment.newInstance()
            }
            Page.ISSUES -> {
                IssuesFragment()
            }
        }
    }

    @StringRes
    fun getItemTabName(position: Int): Int {
        return Page.values()[position].title
    }
}

enum class Page(@StringRes val title: Int) {
    README(R.string.title_readme),
    CONTRIBUTORS(R.string.title_contributors),
    ISSUES(R.string.title_issues)
}