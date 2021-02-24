package krasnikov.project.pmgithubclient.repoinfo.ui.issues

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.app.ui.base.BaseFragment
import krasnikov.project.pmgithubclient.app.ui.base.PaginationScrollListener
import krasnikov.project.pmgithubclient.databinding.FragmentContributorsBinding
import krasnikov.project.pmgithubclient.databinding.FragmentIssuesBinding
import krasnikov.project.pmgithubclient.repoinfo.data.Test
import krasnikov.project.pmgithubclient.repoinfo.ui.contributors.ContributorsAdapter
import krasnikov.project.pmgithubclient.repoinfo.ui.contributors.ContributorsFragment
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile
import krasnikov.project.pmgithubclient.userinfo.ui.UserInfoFragment
import krasnikov.project.pmgithubclient.utils.FragmentArgsDelegate
import krasnikov.project.pmgithubclient.utils.State
import java.lang.IllegalArgumentException

class IssuesFragment : BaseFragment<FragmentIssuesBinding, IssuesViewModel>() {

    private var owner by FragmentArgsDelegate<String>(ARG_OWNER)
    private var repo by FragmentArgsDelegate<String>(ARG_REPO)

    override val viewModel by viewModels<IssuesViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return IssuesViewModel(owner, repo, Test(requireContext()).repositoryService) as T
            }
        }
    }

    private lateinit var issuesAdapter: IssuesAdapter

    override fun setupBinding() {
        binding = FragmentIssuesBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
    }

    private fun setupRecycler() {
        issuesAdapter = IssuesAdapter(viewModel.pagedListIssue)
        with(binding.rvIssues) {
            adapter = issuesAdapter
        }
    }

    companion object {
        private const val ARG_OWNER = "owner"
        private const val ARG_REPO = "repo"

        @JvmStatic
        fun newInstance(owner: String, repo: String) =
            IssuesFragment().apply {
                this.owner = owner
                this.repo = repo
            }
    }
}