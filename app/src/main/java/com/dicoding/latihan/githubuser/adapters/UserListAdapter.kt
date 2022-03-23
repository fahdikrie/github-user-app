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
     * https://stackoverflow.com/questions/60423596/how-to-use-viewbinding-in-a-recyclerview-adapter
     */
    class UserListHolder(
        private val itemBinding: ItemUserBinding
    ): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(user: User) {
            Glide
                .with(this.itemView.context)
                .load(user.avatar)
                .circleCrop()
                .into(itemBinding.imgAvatar)

            itemBinding.tvName.text = user.name

            /**
             * Ini saya bingung harus gimana biar ga ada error:
             *
             * "Do not concatenate text displayed with setText.
             * Use resource string with placeholders."
             *
             * Akhirnya option + enter, lalu "convert assigment
             * to assigment expression" aja :D
             */
            "@${user.username}".also { itemBinding.tvUsername.text = it }
            "📍 ${user.location}".also { itemBinding.tvLocation.text = it }
            "💼 ${user.company}".also { itemBinding.tvCompany.text = it }
            "Repositories: ${user.repositories}".also { itemBinding.tvRepositories.text = it }
            "Following: ${user.following}".also { itemBinding.tvFollowing.text = it }
            "Followers: ${user.followers}".also { itemBinding.tvFollowers.text = it }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListHolder {
        val itemBinding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return UserListHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: UserListHolder, position: Int) {
        val user: User = userList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = userList.size

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}