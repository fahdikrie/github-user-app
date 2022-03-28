package com.dicoding.latihan.githubuser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.latihan.githubuser.databinding.ItemUserBinding
import com.dicoding.latihan.githubuser.models.responses.GithubUser

class UserListAdapter(
    private val userList: List<GithubUser>
) : RecyclerView.Adapter<UserListAdapter.UserListHolder>()  {
    private lateinit var onItemClickCallback: OnItemClickCallback

    /**
     * RecyclerView adapter binding code reference is taken from:
     * https://stackoverflow.com/questions/60423596/
     * how-to-use-viewbinding-in-a-recyclerview-adapter
     */
    class UserListHolder(
        private val binding: ItemUserBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubUser) {
            Glide
                .with(this.itemView.context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.imgAvatar)

            "@${user.login}".also { binding.tvUsername.text = it }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return UserListHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListHolder, position: Int) {
        val user: GithubUser = userList[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(user)
        }
    }

    override fun getItemCount(): Int = userList.size

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUser)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}