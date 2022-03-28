package com.dicoding.latihan.githubuser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.latihan.githubuser.databinding.ItemUserBinding
import com.dicoding.latihan.githubuser.models.responses.GithubUserFollow

class UserDetailFollowAdapter (
    private val userList: List<GithubUserFollow>
) : RecyclerView.Adapter<UserDetailFollowAdapter.UserDetailFollowHolder>()  {

    class UserDetailFollowHolder(
        private val binding: ItemUserBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubUserFollow) {
            Glide
                .with(this.itemView.context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.imgAvatar)

            "@${user.login}".also { binding.tvUsername.text = it }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDetailFollowHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return UserDetailFollowHolder(binding)
    }

    override fun onBindViewHolder(holder: UserDetailFollowHolder, position: Int) {
        val user: GithubUserFollow = userList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = userList.size
}