package com.dicoding.latihan.githubuser.activities

import android.content.Intent
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
        setHeader(userData)
        bindUserData(binding, userData)
        bindFollowButton(binding, userData)
        bindShareButton(binding, userData)
    }

    private fun setHeader(user: User) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = user.name
    }

    private fun bindUserData(binding: ActivityUserDetailBinding, user: User) {
        Glide
            .with(this)
            .load(user.avatar)
            .circleCrop()
            .into(binding.imgDetailAvatar)

        binding.tvDetailName.text = user.name

        "@${user.username}".also { binding.tvDetailUsername.text = it }
        "📍 ${user.location}".also { binding.tvDetailLocation.text = it }
        "💼 ${user.company}".also { binding.tvDetailCompany.text = it }
        "Repositories: ${user.repositories}".also { binding.tvDetailRepositories.text = it }
        "Following: ${user.following}".also { binding.tvDetailFollowing.text = it }
        "Followers: ${user.followers}".also { binding.tvDetailFollowers.text = it }
    }


    private fun bindFollowButton(binding: ActivityUserDetailBinding, user: User) {
        binding.btnDetailFollow.setOnClickListener {
            Toast.makeText(
                this,
                "You are now following ${user.username}!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun bindShareButton(binding: ActivityUserDetailBinding, user: User) {
        binding.btnDetailShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT,
                """
                    See ${user.name}'s GitHub profile:
    
                    https://github.com/${user.username}
                """.trimIndent()
            )

            startActivity(Intent.createChooser(intent,"Share To:"))
        }
    }

    /**
     * Reference code is taken from:
     * https://devofandroid.blogspot.com/2018/03/
     * add-back-button-to-action-bar-android.html
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
