package krasnikov.project.pmgithubclient.repoinfo.ui.contributors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.app.ui.base.PagedListAdapter
import krasnikov.project.pmgithubclient.databinding.RecyclerItemContributorBinding
import krasnikov.project.pmgithubclient.repoinfo.data.model.Contributor
import krasnikov.project.pmgithubclient.utils.PagedList

class ContributorsAdapter(
    pagedList: PagedList<Contributor>,
    private val glideRequestManager: RequestManager
) : PagedListAdapter<Contributor, ContributorsAdapter.ContributorViewHolder>(pagedList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorViewHolder {
        return ContributorViewHolder(
            RecyclerItemContributorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ContributorViewHolder, position: Int) {
        holder.bind(items[position])
        super.onBindViewHolder(holder, position)
    }

    inner class ContributorViewHolder(private val binding: RecyclerItemContributorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val resources = itemView.resources

        fun bind(contributor: Contributor) {
            with(binding) {
                glideRequestManager.load(contributor.avatarUrl).circleCrop().into(ivAvatar)
                tvLogin.text = contributor.login
                tvContributions.text = resources.getString(
                    R.string.text_contributions_number,
                    contributor.contributions
                )
            }
        }
    }
}
