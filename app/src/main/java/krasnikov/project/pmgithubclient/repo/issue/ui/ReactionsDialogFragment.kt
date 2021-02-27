package krasnikov.project.pmgithubclient.repo.issue.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import krasnikov.project.pmgithubclient.databinding.DialogFragmentCommentReactionBinding
import krasnikov.project.pmgithubclient.repo.issue.data.model.ReactionType
import krasnikov.project.pmgithubclient.utils.FragmentArgsDelegate

@AndroidEntryPoint
class ReactionsDialogFragment : DialogFragment() {
    private var commentId by FragmentArgsDelegate<Int>(ARG_ID)
    private var owner by FragmentArgsDelegate<String>(ARG_OWNER)
    private var repo by FragmentArgsDelegate<String>(ARG_REPO)

    private val viewModel: IssueInfoViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )
    lateinit var binding: DialogFragmentCommentReactionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentCommentReactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showReactions()
        setClickListeners()
    }

    private fun showReactions() {
        viewModel.viewModelScope.launch {
            binding.tvReactions.text =
                viewModel.getCommentReactions(owner, repo, commentId).toString()
        }
    }

    private fun createReaction(reactionType: ReactionType) {
        viewModel.viewModelScope.launch {
            binding.tvReactions.text =
                viewModel.createCommentReaction(owner, repo, commentId, reactionType).toString()
        }
    }

    private fun setClickListeners() {
        /*binding.btnReactPlusOne.setOnClickListener {
            createReaction(ReactionType.PlusOne)
        }
        binding.btnReactMinusOne.setOnClickListener {
            createReaction(ReactionType.MinusOne)
        }*/
        binding.rvReactions.setClickCallBack {
            createReaction(it)
        }
    }


    companion object {
        private const val ARG_ID = "ARG_ID"
        private const val ARG_OWNER = "ARG_OWNER"
        private const val ARG_REPO = "ARG_REPO"
        const val TAG = "ReactionsDialogFragment"

        fun newInstance(owner: String, repo: String, commentId: Int) =
            ReactionsDialogFragment().apply {
                this.commentId = commentId
                this.owner = owner
                this.repo = repo
            }
    }
}