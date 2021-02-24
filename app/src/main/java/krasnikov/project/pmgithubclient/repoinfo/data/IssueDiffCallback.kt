package krasnikov.project.pmgithubclient.repoinfo.data

import androidx.recyclerview.widget.DiffUtil
import krasnikov.project.pmgithubclient.repoinfo.data.model.Issue

class IssueDiffCallback : DiffUtil.ItemCallback<Issue>() {
    override fun areItemsTheSame(oldItem: Issue, newItem: Issue): Boolean {
        return oldItem.number == newItem.number
    }

    override fun areContentsTheSame(oldItem: Issue, newItem: Issue): Boolean {
        return oldItem == newItem
    }
}