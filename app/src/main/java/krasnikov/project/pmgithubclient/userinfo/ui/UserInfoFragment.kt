package krasnikov.project.pmgithubclient.userinfo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmgithubclient.app.data.AuthInterceptor
import krasnikov.project.pmgithubclient.app.data.ErrorInterceptor
import krasnikov.project.pmgithubclient.app.data.pref.SharedPref
import krasnikov.project.pmgithubclient.app.di.AppComponent
import krasnikov.project.pmgithubclient.app.ui.base.BaseFragment
import krasnikov.project.pmgithubclient.databinding.FragmentUserInfoBinding
import krasnikov.project.pmgithubclient.userinfo.data.UserInfoRepository
import krasnikov.project.pmgithubclient.userinfo.data.UserService
import krasnikov.project.pmgithubclient.userinfo.data.model.Repo
import krasnikov.project.pmgithubclient.userinfo.data.model.User
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile
import krasnikov.project.pmgithubclient.utils.*
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class UserInfoFragment : BaseFragment<FragmentUserInfoBinding, UserInfoViewModel>() {
    private var userProfile by FragmentArgsDelegate<UserProfile>(ARG_USER_PROFILE)

    override val viewModel: UserInfoViewModel by viewModels()

    override fun setupBinding() {
        binding = FragmentUserInfoBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadUserInfo(userProfile)
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
        val adapter = RepositoriesAdapter(repos).apply {
            onItemClickListener = { viewModel.onRepoClick(it) }
        }
        binding.rvRepo.adapter = adapter
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