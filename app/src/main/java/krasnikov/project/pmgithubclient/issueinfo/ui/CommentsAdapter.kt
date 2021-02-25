package krasnikov.project.pmgithubclient.issueinfo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmgithubclient.app.ui.base.PagedListAdapter
import krasnikov.project.pmgithubclient.databinding.RecycleItemCommentBinding
import krasnikov.project.pmgithubclient.issueinfo.data.model.Comment
import krasnikov.project.pmgithubclient.utils.PagedList

class CommentsAdapter(pagedList: PagedList<Comment>) : PagedListAdapter<Comment, CommentsAdapter.CommentViewHolder>(pagedList) {

    var onItemClickListener: (comment: Comment) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
                RecycleItemCommentBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(items[position])
        super.onBindViewHolder(holder, position)
    }

    inner class CommentViewHolder(private val binding: RecycleItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.tvUser.text = comment.user.login
            binding.tvBody.text = comment.body
            binding.root.setOnClickListener {
                onItemClickListener(comment)
            }
        }
    }


}