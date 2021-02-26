package krasnikov.project.pmgithubclient.repo.issue.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import krasnikov.project.pmgithubclient.app.data.ErrorInterceptor
import krasnikov.project.pmgithubclient.app.di.AppComponent
import krasnikov.project.pmgithubclient.databinding.DialogFragmentCommentReactionBinding
import krasnikov.project.pmgithubclient.repo.issue.data.IssueService
import krasnikov.project.pmgithubclient.repo.issue.data.model.ReactionType
import krasnikov.project.pmgithubclient.utils.FragmentArgsDelegate
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class ReactionsDialogFragment : DialogFragment() {
    private var commentId by FragmentArgsDelegate<Int>(ARG_ID)

    private val viewModel: IssueInfoViewModel by viewModels(
            ownerProducer = {requireParentFragment()}
    )
    lateinit var binding: DialogFragmentCommentReactionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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