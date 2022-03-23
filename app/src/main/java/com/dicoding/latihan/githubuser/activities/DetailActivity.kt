package com.dicoding.latihan.githubuser.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.latihan.githubuser.databinding.ActivityUserDetailBinding
import com.dicoding.latihan.githubuser.models.User

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding

    companion object {
        const val EXTRA_USER_DATA = "extra_user_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userData = intent.getParcelableExtra<User>(EXTRA_USER_DATA)!!
        bindUserData(binding, userData)
        bindFollowButton(binding, userData.username)
    }

    private fun bindUserData(binding: ActivityUserDetailBinding, user: User) {
        Glide
            .with(this)
            .load(user.avatar)
            .circleCrop()
            .into(binding.imgDetailAvatar)

        binding.tvDetailName.text = user.name

        /**
         * Ini saya bingung harus gimana biar ga ada error:
         *
         * "Do not concatenate text displayed with setText.
         * Use resource string with placeholders."
         *
         * Akhirnya option + enter, lalu "convert assigment
         * to assigment expression" aja :D
         */
        "@${user.username}".also { binding.tvDetailUsername.text = it }
        "📍 ${user.location}".also { binding.tvDetailLocation.text = it }
        "💼 ${user.company}".also { binding.tvDetailCompany.text = it }
        "Repositories: ${user.repositories}".also { binding.tvDetailRepositories.text = it }
        "Following: ${user.following}".also { binding.tvDetailFollowing.text = it }
        "Followers: ${user.followers}".also { binding.tvDetailFollowers.text = it }
    }


    private fun bindFollowButton(binding: ActivityUserDetailBinding, username: String) {
        binding.btnDetailFollow.setOnClickListener {
            Toast.makeText(
                this,
                "You are now following $username!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
