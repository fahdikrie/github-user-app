package com.dicoding.latihan.githubuser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.latihan.githubuser.databinding.ItemUserBinding
import com.dicoding.latihan.githubuser.models.User

class UserListAdapter(
    private val userList: ArrayList<User>
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
        fun bind(user: User) {
            Glide
                .with(this.itemView.context)
                .load(user.avatar)
                .circleCrop()
                .into(binding.imgAvatar)

            binding.tvName.text = user.name

            "@${user.username}".also { binding.tvUsername.text = it }
            "📍 ${user.location}".also { binding.tvLocation.text = it }
            "💼 ${user.company}".also { binding.tvCompany.text = it }
            "Repositories: ${user.repositories}".also { binding.tvRepositories.text = it }
            "Following: ${user.following}".also { binding.tvFollowing.text = it }
            "Followers: ${user.followers}".also { binding.tvFollowers.text = it }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return UserListHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListHolder, position: Int) {
        val user: User = userList[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(user)
        }
    }

    override fun getItemCount(): Int = userList.size

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}