package krasnikov.project.pmgithubclient.userinfo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmgithubclient.app.ui.base.PagedListAdapter
import krasnikov.project.pmgithubclient.databinding.RecyclerItemRepoBinding
import krasnikov.project.pmgithubclient.userinfo.data.model.Repo

class RepositoriesAdapter :
    PagedListAdapter<Repo, RepositoriesAdapter.RepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): RepoViewHolder {
        return RepoViewHolder(
            RecyclerItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, repo: Repo) {
        holder.bind(repo)
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