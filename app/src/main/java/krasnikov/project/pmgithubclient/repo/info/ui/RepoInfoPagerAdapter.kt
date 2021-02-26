package krasnikov.project.pmgithubclient.repo.info.ui

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.repo.info.ui.contributors.ContributorsFragment
import krasnikov.project.pmgithubclient.repo.info.ui.issues.IssuesFragment
import krasnikov.project.pmgithubclient.repo.info.ui.readme.ReadMeFragment

class RepoInfoPagerAdapter(
    fragment: Fragment,
    private val owner: String,
    private val repo: String
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = Page.values().size

    override fun createFragment(position: Int): Fragment {
        return when (Page.values()[position]) {
            Page.README -> {
                ReadMeFragment.newInstance(owner, repo)
            }
            Page.CONTRIBUTORS -> {
                ContributorsFragment.newInstance(owner, repo)
            }
            Page.ISSUES -> {
                IssuesFragment.newInstance(owner, repo)
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