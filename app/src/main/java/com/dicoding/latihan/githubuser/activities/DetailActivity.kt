package com.dicoding.latihan.githubuser.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.latihan.githubuser.R
import com.dicoding.latihan.githubuser.databinding.ActivityUserDetailBinding
import com.dicoding.latihan.githubuser.models.User

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityUserDetailBinding? = null
    private val binding get() = _binding!!
    private var userData: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userData = intent.getParcelableExtra(EXTRA_USER_DATA)!!
        setHeader(userData as User)
        bindUserData(binding, userData as User)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_option_share -> shareUser()
        }
        return super.onOptionsItemSelected(item)
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

    private fun shareUser() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(
            Intent.EXTRA_TEXT,
            """
                    See ${userData?.name}'s GitHub profile:
    
                    https://github.com/${userData?.username}
                """.trimIndent()
        )
        startActivity(Intent.createChooser(intent,"Share To:"))
    }

    companion object {
        const val EXTRA_USER_DATA = "extra_user_data"
    }
}
