package krasnikov.project.pmgithubclient.repoinfo.ui.contributors

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.app.ui.base.BaseFragment
import krasnikov.project.pmgithubclient.app.ui.base.PaginationScrollListener
import krasnikov.project.pmgithubclient.databinding.FragmentContributorsBinding
import krasnikov.project.pmgithubclient.repoinfo.data.Test
import krasnikov.project.pmgithubclient.repoinfo.ui.readme.ReadMeViewModel
import krasnikov.project.pmgithubclient.utils.Result
import krasnikov.project.pmgithubclient.utils.State

class ContributorsFragment : BaseFragment<FragmentContributorsBinding, ContributorsViewModel>() {

    override val viewModel by viewModels<ContributorsViewModel>() {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ContributorsViewModel(Test(requireContext()).repositoryService) as T
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
        observeContent()
    }

    private fun setupRecycler() {
        contributorsAdapter = ContributorsAdapter()
        with(binding.rvContributors) {
            adapter = contributorsAdapter
            addOnScrollListener(object :
                PaginationScrollListener(layoutManager as LinearLayoutManager) {
                override fun onLoadMore(page: Int) {
                    viewModel.loadContributors(page)
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
                    contributorsAdapter.addItems(it.data)
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
        fun newInstance() = ContributorsFragment()
    }
}