package krasnikov.project.pmgithubclient.repo.info.ui.contributors

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import krasnikov.project.pmgithubclient.app.di.AppComponent
import krasnikov.project.pmgithubclient.app.ui.base.BaseFragment
import krasnikov.project.pmgithubclient.databinding.FragmentContributorsBinding
import krasnikov.project.pmgithubclient.utils.FragmentArgsDelegate

class ContributorsFragment : BaseFragment<FragmentContributorsBinding, ContributorsViewModel>() {

    private var owner by FragmentArgsDelegate<String>(ARG_OWNER)
    private var repo by FragmentArgsDelegate<String>(ARG_REPO)

    override val viewModel by viewModels<ContributorsViewModel>() {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ContributorsViewModel(owner, repo, AppComponent.repositoryService) as T
            }
        }
    }

    private lateinit var contributorsAdapter: ContributorsAdapter

    override fun setupBinding() {
        binding = FragmentContributorsBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    private fun setupRecycler() {
        contributorsAdapter = ContributorsAdapter(viewModel.pagedListContributors)
        binding.rvContributors.adapter = contributorsAdapter
    }

    companion object {
        private const val ARG_OWNER = "owner"
        private const val ARG_REPO = "repo"

        @JvmStatic
        fun newInstance(owner: String, repo: String) =
            ContributorsFragment().apply {
                this.owner = owner
                this.repo = repo
            }
    }
}