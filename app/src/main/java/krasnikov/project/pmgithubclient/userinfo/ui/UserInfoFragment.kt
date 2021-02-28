package krasnikov.project.pmgithubclient.userinfo.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmgithubclient.app.ui.base.BaseFragment
import krasnikov.project.pmgithubclient.databinding.FragmentUserInfoBinding
import krasnikov.project.pmgithubclient.userinfo.data.model.Repo
import krasnikov.project.pmgithubclient.userinfo.data.model.User
import krasnikov.project.pmgithubclient.userinfo.data.model.UserProfile
import krasnikov.project.pmgithubclient.utils.*

@AndroidEntryPoint
class UserInfoFragment : BaseFragment<FragmentUserInfoBinding, UserInfoViewModel>() {

    private var userProfile by FragmentArgsDelegate<UserProfile>(ARG_USER_PROFILE)

    override val viewModel: UserInfoViewModel by viewModels()

    override fun setupBinding() {
        binding = FragmentUserInfoBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchToolbar()
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

    private fun showLoading() {
        binding.pbLoading.isVisible = true
    }

    private fun hideLoading() {
        binding.pbLoading.isVisible = false
    }

    private fun showUser(user: User) {
        binding.ivAvatar.load(user.avatarUrl)
        binding.tvName.text = user.name
        binding.tvLogin.text = user.login
    }

    private fun showUserRepos(repos: PagedList<Repo>) {
        val adapter = RepositoriesAdapter(binding.rvRepo.handler).apply {
            pagedList = repos
            onItemClickListener = { viewModel.onRepoClick(it.name, getUserLogin()) }
        }
        binding.rvRepo.adapter = adapter
    }

    private fun getUserLogin() = binding.tvLogin.text.toString()

    private fun setupSearchToolbar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        with(binding.toolbar) {
            btnSearch.setOnClickListener {
                searchUser()
            }

            etQuery.setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        searchUser()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun searchUser() {
        with(binding.toolbar.etQuery) {
            clearFocus()
            (requireActivity().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(this.windowToken, 0)
            viewModel.navigateToSearch(text.toString())
        }
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