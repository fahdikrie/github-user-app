package com.dicoding.latihan.githubuser.activities


import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.latihan.githubuser.adapters.UserListAdapter
import com.dicoding.latihan.githubuser.databinding.ActivityMainBinding
import com.dicoding.latihan.githubuser.models.responses.GithubUser
import com.dicoding.latihan.githubuser.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindViewModelToRV()
        bindSearchView()

        if (applicationContext.resources.configuration.orientation ==
            Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUsers.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUsers.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun bindSearchView() {
        binding.svSearchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isBlank()) return false

                showLoading(true)
                mainViewModel.getUsers(query)
                binding.svSearchUser.clearFocus()

                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }
        })
    }

    private fun bindViewModelToRV() {
        mainViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        mainViewModel.userList.observe(this) {
            if (it.isNotEmpty()) showRecyclerList(it)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showRecyclerList(users: List<GithubUser>) {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = UserListAdapter(users)
        binding.rvUsers.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUser) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                startActivity(intentToDetail)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbSearchUser.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}