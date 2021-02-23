package krasnikov.project.pmgithubclient.repoinfo.ui.contributors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmgithubclient.R

class ContributorsAdapter : RecyclerView.Adapter<ContributorsAdapter.ContributorViewHolder>() {

    private val items = mutableSetOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorViewHolder {
        return ContributorViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_contributor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContributorViewHolder, position: Int) {
        holder.bind(items.elementAt(position))
    }

    override fun getItemCount(): Int = items.size

    fun addItems(items: List<User>) {
        val oldCount = itemCount
        this.items.addAll(items)
        notifyItemRangeInserted(oldCount, itemCount - oldCount)
    }

    class ContributorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(contributor: User) {
            itemView.findViewById<AppCompatTextView>(R.id.tvName).text = contributor.login
        }
    }
}
