package krasnikov.project.pmgithubclient.userinfo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.app.data.pref.SharedPref
import krasnikov.project.pmgithubclient.app.ui.base.BaseFragment
import krasnikov.project.pmgithubclient.databinding.FragmentUserInfoBinding
import krasnikov.project.pmgithubclient.login.data.AuthHelper
import krasnikov.project.pmgithubclient.login.ui.LoginViewModel
import krasnikov.project.pmgithubclient.userinfo.data.UserInfoRepository
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile
import krasnikov.project.pmgithubclient.utils.Result
import krasnikov.project.pmgithubclient.utils.State
import java.lang.IllegalArgumentException

class UserInfoFragment : BaseFragment<FragmentUserInfoBinding, UserInfoViewModel>() {

    override val viewModel by viewModels<UserInfoViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return UserInfoViewModel(
                    getArgUserProfile(),
                    UserInfoRepository(requireContext())
                ) as T
            }
        }
    }

    private lateinit var repositoriesAdapter: RepositoriesAdapter

    override fun setupBinding() {
        binding = FragmentUserInfoBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        observeUserContent()
        observeReposContent()
    }

    private fun getArgUserProfile() =
        requireArguments().getParcelable<UserProfile>(ARG_USER_PROFILE)
            ?: throw IllegalArgumentException()

    private fun setupRecycler() {
        repositoriesAdapter = RepositoriesAdapter()
        with(binding.rvRepo) {
            adapter = repositoriesAdapter
        }
    }

    private fun observeUserContent() {
        viewModel.contentUser.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    showLoading()
                }
                is State.Content -> {
                    hideLoading()
                    binding.tvName.text = it.toString()
                }
                is State.Error -> {
                    //showToast(it.error.stringRes)
                }
            }
        }
    }

    private fun observeReposContent() {
        viewModel.contentRepos.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    showLoading()
                }
                is State.Content -> {
                    hideLoading()
                    repositoriesAdapter.addItems(it.data)
                }
                is State.Error -> {
                    //showToast(it.error.stringRes)
                }
            }
        }
    }

    companion object {
        private const val ARG_USER_PROFILE = "user_profile"

        @JvmStatic
        fun newInstance(userProfile: UserProfile) =
            UserInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_USER_PROFILE, userProfile)
                }
            }
    }
}