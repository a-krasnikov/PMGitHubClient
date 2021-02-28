package krasnikov.project.pmgithubclient.repo.info.ui.readme

import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.app.ui.base.BaseFragment
import krasnikov.project.pmgithubclient.databinding.FragmentReadmeBinding
import krasnikov.project.pmgithubclient.utils.FragmentArgsDelegate
import krasnikov.project.pmgithubclient.utils.State
import java.nio.charset.StandardCharsets
import java.util.*

@AndroidEntryPoint
class ReadMeFragment : BaseFragment<FragmentReadmeBinding, ReadMeViewModel>() {

    private var owner by FragmentArgsDelegate<String>(ARG_OWNER)
    private var repo by FragmentArgsDelegate<String>(ARG_REPO)

    override val viewModel by viewModels<ReadMeViewModel>()

    override fun setupBinding() {
        binding = FragmentReadmeBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadReadme(owner, repo)
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
                    val readMe = String(Base64.decode(it.data.content, Base64.DEFAULT) ,StandardCharsets.UTF_8)
                    binding.tvText.text = readMe
                }
                is State.Error -> {
                    hideLoading()
                    showToast(R.string.toast_login_error)
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