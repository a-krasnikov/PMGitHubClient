package krasnikov.project.pmgithubclient.userinfo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmgithubclient.app.ui.base.PagedListAdapter
import krasnikov.project.pmgithubclient.databinding.RecyclerItemRepoBinding
import krasnikov.project.pmgithubclient.userinfo.data.model.Repo
import krasnikov.project.pmgithubclient.utils.PagedList

class RepositoriesAdapter(pagedList: PagedList<Repo>) :
    PagedListAdapter<Repo, RepositoriesAdapter.RepoViewHolder>(pagedList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            RecyclerItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(items[position])
        super.onBindViewHolder(holder, position)
    }

    class RepoViewHolder(private val binding: RecyclerItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: Repo) {
            with(binding) {
                tvTitle.text = repo.name
                tvDescription.text = repo.description
            }
        }
    }
}