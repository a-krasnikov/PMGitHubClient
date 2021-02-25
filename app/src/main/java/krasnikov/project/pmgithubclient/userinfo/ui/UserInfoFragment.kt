package krasnikov.project.pmgithubclient.userinfo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import krasnikov.project.pmgithubclient.app.di.AppComponent
import krasnikov.project.pmgithubclient.app.ui.base.BaseFragment
import krasnikov.project.pmgithubclient.databinding.FragmentUserInfoBinding
import krasnikov.project.pmgithubclient.userinfo.data.UserInfoRepository
import krasnikov.project.pmgithubclient.userinfo.data.model.Repo
import krasnikov.project.pmgithubclient.userinfo.data.model.User
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile
import krasnikov.project.pmgithubclient.utils.*

class UserInfoFragment : BaseFragment<FragmentUserInfoBinding, UserInfoViewModel>() {

    private var userProfile by FragmentArgsDelegate<UserProfile>(ARG_USER_PROFILE)

    override val viewModel by viewModels<UserInfoViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repository = UserInfoRepository(AppComponent.userService)
                return UserInfoViewModel(
                    userProfile,
                    repository
                ) as T
            }
        }
    }

    override fun setupBinding() {
        binding = FragmentUserInfoBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUserContent()
    }

    private fun observeUserContent() {
        viewModel.contentUser.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    showLoading()
                }
                is State.Content -> {
                    hideLoading()
                    showUser(it.data.user)
                    showUserRepos(it.data.repos)
                }
                is State.Error -> {
                    showToast(it.error.stringRes)
                    hideLoading()
                }
            }
        }
    }

    private fun showUser(user: User) {
        binding.ivAvatar.load(user.avatarUrl)
        binding.tvName.text = user.name
        binding.tvLogin.text = user.login
    }

    private fun showUserRepos(repos: PagedList<Repo>) {
        binding.rvRepo.adapter = RepositoriesAdapter(repos)
    }

    companion object {
        private const val ARG_USER_PROFILE = "user_profile"

        @JvmStatic
        fun newInstance(userProfile: UserProfile) =
            UserInfoFragment().apply {
                this.userProfile = userProfile
            }
    }
}