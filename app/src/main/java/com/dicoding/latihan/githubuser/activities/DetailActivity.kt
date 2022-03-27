package com.dicoding.latihan.githubuser.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.latihan.githubuser.R
import com.dicoding.latihan.githubuser.adapters.UserDetailPagerAdapter
import com.dicoding.latihan.githubuser.databinding.ActivityUserDetailBinding
import com.dicoding.latihan.githubuser.models.User
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityUserDetailBinding? = null
    private val binding get() = _binding!!
    private var userData: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        userData = intent.getParcelableExtra(EXTRA_USER_DATA)!!
        setHeader()
        bindUserData()

        val sectionsPagerAdapter = UserDetailPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
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
        menuInflater.inflate(R.menu.option_menu_user_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_option_share -> shareUser()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setHeader() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /**
         * Reference code is taken from:
         * https://stackoverflow.com/questions/31662416/
         * show-collapsingtoolbarlayout-title-only-when-collapsed
         */
        var isShow = true
        var scrollRange = -1
        binding.appBar.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
                if (scrollRange == -1) {
                    scrollRange = barLayout?.totalScrollRange!!
                }

                if (scrollRange + verticalOffset == 0){
                    binding.toolbarLayout.title = userData?.name
                    isShow = true
                } else if (isShow){
                    binding.toolbarLayout.title = " "
                    isShow = false
                }
            }
        )
    }

    private fun bindUserData() {
        Glide
            .with(this)
            .load(userData?.avatar)
            .circleCrop()
            .into(binding.imgDetailAvatar)

        binding.tvDetailName.text = userData?.name

        "@${userData?.username}".also { binding.tvDetailUsername.text = it }
        "📍 ${userData?.location}".also { binding.tvDetailLocation.text = it }
        "💼 ${userData?.company}".also { binding.tvDetailCompany.text = it }
        "Repositories: ${userData?.repositories}".also { binding.tvDetailRepositories.text = it }
        "Following: ${userData?.following}".also { binding.tvDetailFollowing.text = it }
        "Followers: ${userData?.followers}".also { binding.tvDetailFollowers.text = it }
    }

    private fun shareUser(): Boolean {
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
        return true
    }

    companion object {
        const val EXTRA_USER_DATA = "extra_user_data"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_user_detail_following,
            R.string.tab_user_detail_followers
        )
    }
}
