package com.dicoding.latihan.githubuser.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.latihan.githubuser.R
import com.dicoding.latihan.githubuser.adapters.UserDetailPagerAdapter
import com.dicoding.latihan.githubuser.databinding.ActivityUserDetailBinding
import com.dicoding.latihan.githubuser.models.responses.GithubUserDetailResponse
import com.dicoding.latihan.githubuser.models.room.entities.FavoriteUserEntity
import com.dicoding.latihan.githubuser.viewmodels.DetailViewModel
import com.dicoding.latihan.githubuser.viewmodels.FavoriteUserViewModel
import com.dicoding.latihan.githubuser.viewmodels.factories.FavoriteUserViewModelFactory
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityUserDetailBinding? = null
    private val binding get() = _binding!!
    private var userDetailData: GithubUserDetailResponse? = null
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var username: String
    private lateinit var favoriteUserViewModelFactory: FavoriteUserViewModelFactory
    private val favoriteUserViewModel: FavoriteUserViewModel by viewModels {
        favoriteUserViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = " "

        username = intent.getStringExtra(EXTRA_USERNAME)!!
        bindDetailViewModelData()
        bindFavoriteUserViewModelData()
        setTabLayout()
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

    private fun bindDetailViewModelData() {
        detailViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        detailViewModel.getUser(username)

        detailViewModel.userDetail.observe(this) {
            bindUserData(it)
            setHeader(it)
            userDetailData = it
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun bindFavoriteUserViewModelData() {
        favoriteUserViewModelFactory = FavoriteUserViewModelFactory.getInstance(this)
        favoriteUserViewModel.getAllFavoriteUsers().observe(this) {
            val isUserInFavorite = it.any { user -> user.login == username }
            setFloatingActionButtonOnClickListener(isUserInFavorite)
            setFloatingActionButtonImage(isUserInFavorite)
        }
    }

    private fun setFloatingActionButtonOnClickListener(isUserInFavorite: Boolean) {
        binding.fabFavoriteBtn.setOnClickListener {
            if (username.isBlank() || userDetailData == null) return@setOnClickListener

            when (!isUserInFavorite) {
                true -> {
                    val favoriteUserEntity = FavoriteUserEntity(
                        login = username,
                        avatarUrl = userDetailData?.avatarUrl
                    )
                    favoriteUserViewModel.setAsFavoriteUser(favoriteUserEntity)
                    setFloatingActionButtonImage(true)
                    showFavoriteInteractionToast(true)
                }
                false -> {
                    favoriteUserViewModel.unsetFavoriteUser(username)
                    setFloatingActionButtonImage(false)
                    showFavoriteInteractionToast(false)
                }
            }
        }
    }

    private fun setFloatingActionButtonImage(isUserInFavorite: Boolean) {
        val drawable = when (isUserInFavorite) {
            true -> R.drawable.ic_baseline_favorite_24
            false -> R.drawable.ic_baseline_favorite_border_24
        }

        binding.fabFavoriteBtn.setImageDrawable(
            ContextCompat.getDrawable(
                this@DetailActivity,
                drawable
            )
        )
    }

    private fun showFavoriteInteractionToast(isUserInFavorite: Boolean) {
        val text = when (isUserInFavorite) {
            true -> "User @$username is added to your Favorites!"
            false -> "User @$username is removed from your Favorites!"
        }

        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    private fun setTabLayout() {
        val sectionsPagerAdapter = UserDetailPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setHeader(userData: GithubUserDetailResponse) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

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
                    binding.toolbarLayout.title = userData.login
                    isShow = true
                } else if (isShow){
                    binding.toolbarLayout.title = " "
                    isShow = false
                }
            }
        )
    }

    private fun bindUserData(userData: GithubUserDetailResponse) {
        Glide
            .with(this)
            .load(userData.avatarUrl)
            .circleCrop()
            .into(binding.imgDetailAvatar)

        binding.tvDetailName.text = userData.name

        "@${userData.login}".also { binding.tvDetailUsername.text = it }
        "📍 ${userData.location  ?: "-"}".also { binding.tvDetailLocation.text = it }
        "💼 ${userData.company ?: "-"}".also { binding.tvDetailCompany.text = it }
        "Repositories: ${userData.publicRepos}".also { binding.tvDetailRepositories.text = it }
        "Following: ${userData.following}".also { binding.tvDetailFollowing.text = it }
        "Followers: ${userData.followers}".also { binding.tvDetailFollowers.text = it }
    }

    private fun shareUser(): Boolean {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(
            Intent.EXTRA_TEXT,
            """
                See ${userDetailData?.name}'s GitHub profile:

                https://github.com/${userDetailData?.login}
            """.trimIndent()
        )
        startActivity(Intent.createChooser(intent,"Share To:"))
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbDetailUser.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_user_detail_followers,
            R.string.tab_user_detail_following
        )
    }
}
