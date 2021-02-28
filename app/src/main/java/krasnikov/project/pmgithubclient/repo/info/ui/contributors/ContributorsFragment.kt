package krasnikov.project.pmgithubclient.repo.info.ui.contributors

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmgithubclient.app.ui.base.BaseFragment
import krasnikov.project.pmgithubclient.databinding.FragmentContributorsBinding
import krasnikov.project.pmgithubclient.utils.FragmentArgsDelegate

@AndroidEntryPoint
class ContributorsFragment : BaseFragment<FragmentContributorsBinding, ContributorsViewModel>() {

    private var owner by FragmentArgsDelegate<String>(ARG_OWNER)
    private var repo by FragmentArgsDelegate<String>(ARG_REPO)

    override val viewModel by viewModels<ContributorsViewModel>()

    private lateinit var contributorsAdapter: ContributorsAdapter

    override fun setupBinding() {
        binding = FragmentContributorsBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    private fun setupRecycler() {
        contributorsAdapter = ContributorsAdapter(binding.rvContributors.handler).apply {
            pagedList = viewModel.loadContributors(owner, repo)
            onItemClickListener = {
                viewModel.navigateToUserInfo(it.login)
            }
        }
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