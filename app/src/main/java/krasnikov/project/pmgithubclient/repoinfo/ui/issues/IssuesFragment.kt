package krasnikov.project.pmgithubclient.repoinfo.ui.issues

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
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
import krasnikov.project.pmgithubclient.utils.State

class IssuesFragment : BaseFragment<FragmentIssuesBinding, IssuesViewModel>() {
    override val viewModel by viewModels<IssuesViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return IssuesViewModel(Test(requireContext()).repositoryService) as T
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
        observeContent()
    }

    private fun setupRecycler() {
        issuesAdapter = IssuesAdapter()
        with(binding.rvIssues) {
            adapter = issuesAdapter
            addOnScrollListener(object :
                PaginationScrollListener(layoutManager as LinearLayoutManager) {
                override fun onLoadMore(page: Int) {
                    viewModel.loadIssues(page)
                }
            })
        }
    }

    private fun observeContent() {
        viewModel.content.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    showLoading()
                }
                is State.Content -> {
                    hideLoading()
                    issuesAdapter.addItems(it.data)
                }
                is State.Error -> {
                    hideLoading()
                    showToast(R.string.toast_login_error)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = IssuesFragment()
    }
}