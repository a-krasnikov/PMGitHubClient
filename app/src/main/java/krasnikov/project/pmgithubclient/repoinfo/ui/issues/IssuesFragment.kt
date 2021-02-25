package krasnikov.project.pmgithubclient.repoinfo.ui.issues

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import krasnikov.project.pmgithubclient.app.di.AppComponent
import krasnikov.project.pmgithubclient.app.ui.base.BaseFragment
import krasnikov.project.pmgithubclient.databinding.FragmentIssuesBinding
import krasnikov.project.pmgithubclient.utils.FragmentArgsDelegate

class IssuesFragment : BaseFragment<FragmentIssuesBinding, IssuesViewModel>() {

    private var owner by FragmentArgsDelegate<String>(ARG_OWNER)
    private var repo by FragmentArgsDelegate<String>(ARG_REPO)

    override val viewModel by viewModels<IssuesViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return IssuesViewModel(owner, repo, AppComponent.repositoryService) as T
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