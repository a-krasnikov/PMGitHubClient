package krasnikov.project.pmgithubclient.repo.info.ui.contributors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.app.ui.base.PagedListAdapter
import krasnikov.project.pmgithubclient.databinding.RecyclerItemContributorBinding
import krasnikov.project.pmgithubclient.repo.info.data.model.Contributor
import krasnikov.project.pmgithubclient.utils.load

class ContributorsAdapter :
    PagedListAdapter<Contributor, ContributorsAdapter.ContributorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): ContributorViewHolder {
        return ContributorViewHolder(
            RecyclerItemContributorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ContributorViewHolder, contributor: Contributor) {
        holder.bind(contributor)
    }

    class ContributorViewHolder(private val binding: RecyclerItemContributorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val resources = itemView.resources

        fun bind(contributor: Contributor) {
            with(binding) {
                ivAvatar.load(contributor.avatarUrl)
                tvLogin.text = contributor.login
                tvContributions.text = resources.getString(
                    R.string.text_contributions_number,
                    contributor.contributions
                )
            }
        }
    }
}
