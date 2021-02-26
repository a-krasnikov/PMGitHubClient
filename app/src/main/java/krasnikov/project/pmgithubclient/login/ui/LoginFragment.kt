package krasnikov.project.pmgithubclient.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.app.data.pref.SharedPref
import krasnikov.project.pmgithubclient.app.ui.base.BaseFragment
import krasnikov.project.pmgithubclient.databinding.FragmentLoginBinding
import krasnikov.project.pmgithubclient.login.data.AuthHelper
import krasnikov.project.pmgithubclient.utils.State

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel by viewModels<LoginViewModel>() {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return LoginViewModel(AuthHelper(), SharedPref(requireContext())) as T
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.handleOauth(requireActivity().intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBtnListener()
        observeContent()
    }

    override fun setupBinding() {
        binding = FragmentLoginBinding.inflate(layoutInflater)
    }

    private fun setupBtnListener() {
        binding.btnLogin.setOnClickListener {
            startGitHubLogin()
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
                    showToast(R.string.toast_login_successful)
                }
                is State.Error -> {
                    hideLoading()
                    //showToast(it.error.stringRes)
                    showToast(R.string.toast_login_error)
                }
            }
        }
    }

    private fun startGitHubLogin() {
        val authIntent = Intent(Intent.ACTION_VIEW, viewModel.authGitHubUrl)
        startActivity(authIntent)
    }
}