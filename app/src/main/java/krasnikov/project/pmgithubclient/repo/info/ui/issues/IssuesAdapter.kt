package krasnikov.project.pmgithubclient.repo.info.ui.issues

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.app.ui.base.PagedListAdapter
import krasnikov.project.pmgithubclient.databinding.RecyclerItemIssueBinding
import krasnikov.project.pmgithubclient.repo.info.data.model.Issue
import krasnikov.project.pmgithubclient.utils.PagedList

class IssuesAdapter : PagedListAdapter<Issue, IssuesAdapter.IssueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): IssueViewHolder {
        return IssueViewHolder(
            RecyclerItemIssueBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IssueViewHolder, issue: Issue) {
        holder.bind(issue)
    }

    class IssueViewHolder(private val binding: RecyclerItemIssueBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val resources = itemView.resources

        fun bind(issue: Issue) {
            with(binding) {
                tvTitle.text = issue.title
                tvNumber.text = resources.getString(R.string.text_issue_number, issue.number)
                tvBody.text = issue.body
            }
        }
    }
}