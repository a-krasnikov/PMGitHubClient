package krasnikov.project.pmgithubclient.search.ui

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmgithubclient.app.ui.base.PagedListAdapter
import krasnikov.project.pmgithubclient.databinding.RecyclerItemUserBinding
import krasnikov.project.pmgithubclient.userinfo.data.model.User
import krasnikov.project.pmgithubclient.utils.load

class UsersAdapter(rvHandler: Handler) :
    PagedListAdapter<User, UsersAdapter.UserViewHolder>(rvHandler) {

    override fun onCreateViewHolder(parent: ViewGroup): UserViewHolder {
        return UserViewHolder(
            RecyclerItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
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

    override fun onBindViewHolder(holder: UserViewHolder, user: User) {
        holder.bind(user)
    }
}