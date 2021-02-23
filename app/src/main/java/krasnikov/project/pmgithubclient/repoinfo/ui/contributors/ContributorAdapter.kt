package krasnikov.project.pmgithubclient.repoinfo.ui.contributors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.repoinfo.data.model.ReadMeModel
/*

class ContributorAdapter : RecyclerView.Adapter<ContributorAdapter.ContributorViewHolder>() {

    val items = mutableListOf<ReadMeModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorViewHolder {
        return ContributorViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_contributor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContributorViewHolder, position: Int) {
        holder.bind(items.get(position))
    }

    override fun getItemCount(): Int = items.size

    fun addItems(items: List<ReadMeModel>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ContributorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(contributor: ReadMeModel) {
            itemView.findViewById<AppCompatTextView>(R.id.tvName).text = contributor.login
        }
    }
}*/
