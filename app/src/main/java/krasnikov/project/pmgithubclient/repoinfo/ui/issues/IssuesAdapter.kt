package krasnikov.project.pmgithubclient.repoinfo.ui.issues

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.repoinfo.data.model.IssueModel

class IssuesAdapter : RecyclerView.Adapter<IssuesAdapter.IssueViewHolder>() {

    private val items = mutableSetOf<IssueModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        return IssueViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_contributor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(items.elementAt(position))
    }

    override fun getItemCount(): Int = items.size

    fun addItems(items: List<IssueModel>) {
        val oldCount = itemCount
        this.items.addAll(items)
        notifyItemRangeInserted(oldCount, itemCount - oldCount)
    }

    class IssueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(contributor: IssueModel) {
            itemView.findViewById<AppCompatTextView>(R.id.tvName).text = contributor.title
        }
    }
}