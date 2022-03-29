package com.dicoding.latihan.githubuser.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.latihan.githubuser.adapters.FavoriteUserAdapter
import com.dicoding.latihan.githubuser.databinding.ActivityFavoriteUserBinding
import com.dicoding.latihan.githubuser.models.room.entities.FavoriteUserEntity
import com.dicoding.latihan.githubuser.viewmodels.FavoriteUserViewModel
import com.dicoding.latihan.githubuser.viewmodels.factories.FavoriteUserViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {
    private var _binding: ActivityFavoriteUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteUserViewModelFactory: FavoriteUserViewModelFactory
    private val favoriteUserViewModel: FavoriteUserViewModel by viewModels {
        favoriteUserViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Favorite Users"
        bindViewModelToRV()
    }

    private fun bindViewModelToRV() {
        favoriteUserViewModelFactory = FavoriteUserViewModelFactory.getInstance(this)
        favoriteUserViewModel.getAllFavoriteUsers().observe(this) {
            showRecyclerList(it)
        }
    }

    private fun showRecyclerList(users: List<FavoriteUserEntity>) {
        binding.rvFavoriteUser.layoutManager = LinearLayoutManager(this)
        val userListAdapter = FavoriteUserAdapter(users)
        binding.rvFavoriteUser.adapter = userListAdapter

        userListAdapter.setOnItemClickCallback(object : FavoriteUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteUserEntity) {
                val intentToDetail = Intent(
                    this@FavoriteUserActivity,
                    DetailActivity::class.java
                )
                intentToDetail.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                startActivity(intentToDetail)
            }
        })
    }
}