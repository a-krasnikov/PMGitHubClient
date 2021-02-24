package krasnikov.project.pmgithubclient.repoinfo.ui.readme

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.app.ui.base.BaseFragment
import krasnikov.project.pmgithubclient.databinding.FragmentReadmeBinding
import krasnikov.project.pmgithubclient.repoinfo.data.Test
import krasnikov.project.pmgithubclient.utils.FragmentArgsDelegate
import krasnikov.project.pmgithubclient.utils.State

class ReadMeFragment : BaseFragment<FragmentReadmeBinding, ReadMeViewModel>() {

    private var owner by FragmentArgsDelegate<String>(ARG_OWNER)
    private var repo by FragmentArgsDelegate<String>(ARG_REPO)

    override val viewModel by viewModels<ReadMeViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ReadMeViewModel(owner, repo, Test(requireContext()).repositoryService) as T
            }
        }
    }

    override fun setupBinding() {
        binding = FragmentReadmeBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeContent()
    }

    private fun observeContent() {
        viewModel.content.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    showLoading()
                }
                is State.Content -> {
                    hideLoading()
                    binding.tvText.text = it.data.content
                }
                is State.Error -> {
                    hideLoading()
                    showToast(R.string.toast_login_error)
                }
            }
        }
    }

    companion object {
        private const val ARG_OWNER = "owner"
        private const val ARG_REPO = "repo"

        @JvmStatic
        fun newInstance(owner: String, repo: String) =
            ReadMeFragment().apply {
                this.owner = owner
                this.repo = repo
            }
    }
}