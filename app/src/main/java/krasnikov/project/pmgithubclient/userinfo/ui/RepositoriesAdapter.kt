package krasnikov.project.pmgithubclient.userinfo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.userinfo.data.model.Repo

class RepositoriesAdapter : RecyclerView.Adapter<RepositoriesAdapter.RepositoryViewHolder>() {

    private val items = mutableSetOf<Repo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_repo, parent, false)
        return RepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(items.elementAt(position))
    }

    override fun getItemCount(): Int = items.size

    fun addItems(items: List<Repo>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class RepositoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(repo: Repo) {
            itemView.findViewById<AppCompatTextView>(R.id.tvTitle).text = repo.name
        }
    }
}