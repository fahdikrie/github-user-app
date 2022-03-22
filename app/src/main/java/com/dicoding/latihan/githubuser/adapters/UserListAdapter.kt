package com.dicoding.latihan.githubuser.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.latihan.githubuser.R
import com.dicoding.latihan.githubuser.databinding.ItemUserBinding
import com.dicoding.latihan.githubuser.models.User

class UserListAdapter(
    private val userList: ArrayList<User>
) : RecyclerView.Adapter<UserListAdapter.UserListHolder>()  {

    /**
     * RecyclerView adapter binding code reference is taken from:
     * https://stackoverflow.com/questions/60423596/how-to-use-viewbinding-in-a-recyclerview-adapter
     */
    class UserListHolder(
        private val itemBinding: ItemUserBinding
    ): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(user: User) {
            itemBinding.tvUsername.text = user.username
            itemBinding.tvName.text = user.name

            /**
             * Ini saya bingung harus gimana biar ga ada error:
             *
             * "Do not concatenate text displayed with setText.
             * Use resource string with placeholders."
             */
            "📍 ${user.location}".also { itemBinding.tvLocation.text = it }
            "💼 ${user.company}".also { itemBinding.tvCompany.text = it }
            "Repositories: ${user.repositories}".also { itemBinding.tvRepositories.text = it }
            "Following: ${user.following}".also { itemBinding.tvFollowing.text = it }
            "Followers: ${user.followers}".also { itemBinding.tvFollowing.text = it }

            itemBinding.imgAvatar.setImageResource(user.avatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserListHolder(view)
    }

    override fun onBindViewHolder(holder: UserListHolder, position: Int) {

    }

    override fun getItemCount(): Int = userList.size
}