package krasnikov.project.pmgithubclient.repo.issue.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.databinding.ViewReactionsBinding
import krasnikov.project.pmgithubclient.repo.issue.data.model.Reaction
import krasnikov.project.pmgithubclient.repo.issue.data.model.ReactionType

class ReactionsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttrs: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttrs, defStyleRes) {

    private val binding: ViewReactionsBinding

    private var clickCallBack: (ReactionType) -> (Unit) = { _ -> }

    init {
        val view = inflate(context, R.layout.view_reactions, this)
        binding = ViewReactionsBinding.bind(view)

        binding.tvPlusOne.setupEmojiTextView(ReactionType.PlusOne)

        binding.tvMinusOne.setupEmojiTextView(ReactionType.MinusOne)

        binding.tvLaugh.setupEmojiTextView(ReactionType.Laugh)

        binding.tvConfused.setupEmojiTextView(ReactionType.Confused)

        binding.tvHeart.setupEmojiTextView(ReactionType.Heart)

        binding.tvHooray.setupEmojiTextView(ReactionType.Hooray)

        binding.tvRocket.setupEmojiTextView(ReactionType.Rocket)

        binding.tvEyes.setupEmojiTextView(ReactionType.Eyes)
    }

    private fun AppCompatTextView.setupEmojiTextView(reactionType: ReactionType) {
        this.apply {
            setOnClickListener(getClickListener(reactionType))
            text = getEmojiText(reactionType)
        }
    }

    private fun getEmojiText(reactionType: ReactionType, count: Int = 0): String {
        val emojiText = String(Character.toChars(reactionType.emoji))
        return context.getString(R.string.reaction_and_count, emojiText, count)
    }

    private fun getClickListener(reactionType: ReactionType): OnClickListener {
        return OnClickListener { clickCallBack(reactionType) }
    }

    fun setClickCallBack(callBack: (ReactionType) -> (Unit)) {
        clickCallBack = callBack
    }


    fun updateReactions(list: List<Reaction>) {
        val reactionMap = hashMapOf<String, Int>()
        list.forEach { reaction ->
            val n = reactionMap[reaction.content]
            n?.let {
                reactionMap[reaction.content] = n + 1
            } ?: run {
                reactionMap[reaction.content] = 1
            }
        }

        reactionMap.forEach { (content, count) ->
            when (content) {
                ReactionType.PlusOne.content -> {
                    binding.tvPlusOne
                        .text = getEmojiText(ReactionType.PlusOne, count)
                }
                ReactionType.MinusOne.content -> {
                    binding.tvMinusOne
                        .text = getEmojiText(ReactionType.MinusOne, count)
                }
                ReactionType.Laugh.content -> {
                    binding.tvLaugh
                        .text = getEmojiText(ReactionType.Laugh, count)
                }
                ReactionType.Confused.content -> {
                    binding.tvConfused
                        .text = getEmojiText(ReactionType.Confused, count)
                }
                ReactionType.Heart.content -> {
                    binding.tvHeart
                        .text = getEmojiText(ReactionType.Heart, count)
                }
                ReactionType.Hooray.content -> {
                    binding.tvHooray
                        .text = getEmojiText(ReactionType.Hooray, count)
                }
                ReactionType.Rocket.content -> {
                    binding.tvRocket
                        .text = getEmojiText(ReactionType.Rocket, count)
                }
                ReactionType.Eyes.content -> {
                    binding.tvEyes
                        .text = getEmojiText(ReactionType.Eyes, count)
                }
            }
        }
    }
}