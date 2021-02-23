package krasnikov.project.pmgithubclient.repoinfo.ui.readme

import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.app.data.pref.SharedPref
import krasnikov.project.pmgithubclient.app.ui.base.BaseFragment
import krasnikov.project.pmgithubclient.databinding.FragmentReadmeBinding
import krasnikov.project.pmgithubclient.login.data.AuthHelper
import krasnikov.project.pmgithubclient.login.ui.LoginViewModel
import krasnikov.project.pmgithubclient.repoinfo.data.Test
import krasnikov.project.pmgithubclient.utils.State

class ReadMeFragment : BaseFragment<FragmentReadmeBinding, ReadMeViewModel>() {

    override val viewModel by viewModels<ReadMeViewModel>() {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ReadMeViewModel(Test(requireContext()).repositoryService) as T
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
        @JvmStatic
        fun newInstance() = ReadMeFragment()
    }
}