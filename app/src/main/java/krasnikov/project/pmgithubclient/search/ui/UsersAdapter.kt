package krasnikov.project.pmgithubclient.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmgithubclient.app.ui.base.PagedListAdapter
import krasnikov.project.pmgithubclient.databinding.RecyclerItemUserBinding
import krasnikov.project.pmgithubclient.userinfo.data.model.User
import krasnikov.project.pmgithubclient.utils.PagedList
import krasnikov.project.pmgithubclient.utils.load

class UsersAdapter(pagedList: PagedList<User>) :
    PagedListAdapter<User, UsersAdapter.UserViewHolder>(pagedList) {

    var onItemClickListener: (user: User) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            RecyclerItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onItemClickListener(items[position])
        }
    }

    class UserViewHolder(private val binding: RecyclerItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            with(binding) {
                ivAvatar.load(user.avatarUrl)
                tvLogin.text = user.login
            }
        }
    }
}