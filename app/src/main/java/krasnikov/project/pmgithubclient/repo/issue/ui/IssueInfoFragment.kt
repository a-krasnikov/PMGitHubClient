package krasnikov.project.pmgithubclient.repo.issue.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmgithubclient.app.ui.base.BaseFragment
import krasnikov.project.pmgithubclient.databinding.FragmentIssueInfoBinding
import krasnikov.project.pmgithubclient.repo.issue.data.model.Comment
import krasnikov.project.pmgithubclient.repo.info.data.model.Issue
import krasnikov.project.pmgithubclient.utils.FragmentArgsDelegate

@AndroidEntryPoint
class IssueInfoFragment : BaseFragment<FragmentIssueInfoBinding, IssueInfoViewModel>() {
    private var owner by FragmentArgsDelegate<String>(ARG_OWNER)
    private var repo by FragmentArgsDelegate<String>(ARG_REPO)
    private var issue by FragmentArgsDelegate<Issue>(ARG_ISSUE)


    private lateinit var commentAdapter: CommentsAdapter

    override val viewModel by viewModels<IssueInfoViewModel> ()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showIssueInfo()
        setupRecycler()
    }

    override fun setupBinding() {
        binding = FragmentIssueInfoBinding.inflate(layoutInflater)
    }

    private fun setupRecycler() {
        commentAdapter = CommentsAdapter().apply {
            pagedList = viewModel.getIssueComments(owner, repo, issue)
            onItemClickListener = { comment ->
                openReactionDialog(comment)
            }
        }
        binding.rvComments.adapter = commentAdapter
    }

    private fun showIssueInfo() {
        with(binding) {
            tvName.text = issue.title
            tvBody.text = issue.body
        }
    }

    private fun openReactionDialog(comment: Comment) {

        childFragmentManager.commit {
            add(ReactionsDialogFragment.newInstance(owner, repo, comment.id),
                    ReactionsDialogFragment.TAG)
        }
    }


    companion object {
        private const val ARG_OWNER = "ARG_OWNER"
        private const val ARG_REPO = "ARG_REPO"
        private const val ARG_ISSUE = "ARG_ISSUE"

        fun newInstance(owner: String, repo: String, issue: Issue) =
                IssueInfoFragment().apply {
                    this.owner = owner
                    this.repo = repo
                    this.issue = issue
                }
    }
}