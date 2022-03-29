package com.dicoding.latihan.githubuser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.latihan.githubuser.databinding.ItemUserBinding
import com.dicoding.latihan.githubuser.models.room.entities.FavoriteUserEntity

class FavoriteUserAdapter(
    private val favoriteUserList: List<FavoriteUserEntity>
) : RecyclerView.Adapter<FavoriteUserAdapter.UserListHolder>()  {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class UserListHolder(
        private val binding: ItemUserBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FavoriteUserEntity) {
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
        val user: FavoriteUserEntity = favoriteUserList[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(user)
        }
    }

    override fun getItemCount(): Int = favoriteUserList.size

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteUserEntity)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}