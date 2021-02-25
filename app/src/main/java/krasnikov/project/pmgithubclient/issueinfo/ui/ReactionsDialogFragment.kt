package krasnikov.project.pmgithubclient.issueinfo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import krasnikov.project.pmgithubclient.databinding.DialogFragmentCommentReactionBinding
import krasnikov.project.pmgithubclient.issueinfo.data.model.ReactionType
import krasnikov.project.pmgithubclient.utils.FragmentArgsDelegate

class ReactionsDialogFragment : DialogFragment() {
    private var commentId by FragmentArgsDelegate<Int>(ARG_ID)

    private val viewModel: IssueInfoViewModel by activityViewModels()
    lateinit var binding: DialogFragmentCommentReactionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
            binding.tvReactions.text = viewModel.getCommentReactions(commentId).toString()
        }
    }

    private fun createReaction(reactionType: ReactionType) {
        viewModel.viewModelScope.launch {
            binding.tvReactions.text = viewModel.createCommentReaction(commentId, reactionType).toString()
        }
    }

    private fun setClickListeners() {
        binding.btnReactPlusOne.setOnClickListener {
            createReaction(ReactionType.PlusOne)
        }
        binding.btnReactMinusOne.setOnClickListener {
            createReaction(ReactionType.MinusOne)
        }
    }

    companion object {
        private const val ARG_ID = "ARG_ID"
        const val TAG = "ReactionsDialogFragment"

        fun newInstance(commentId: Int) =
                ReactionsDialogFragment().apply {
                    this.commentId = commentId
                }
    }
}