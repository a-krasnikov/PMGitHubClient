package krasnikov.project.pmgithubclient.repo.issue.ui

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmgithubclient.app.ui.base.PagedListAdapter
import krasnikov.project.pmgithubclient.databinding.RecycleItemCommentBinding
import krasnikov.project.pmgithubclient.repo.issue.data.model.Comment

class CommentsAdapter(rvHandler: Handler) :
    PagedListAdapter<Comment, CommentsAdapter.CommentViewHolder>(rvHandler) {

    override fun onCreateViewHolder(parent: ViewGroup): CommentViewHolder {
        return CommentViewHolder(
            RecycleItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, comment: Comment) {
        holder.bind(comment)
    }

    class CommentViewHolder(private val binding: RecycleItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.tvUser.text = comment.user.login
            binding.tvBody.text = comment.body
        }
    }
}