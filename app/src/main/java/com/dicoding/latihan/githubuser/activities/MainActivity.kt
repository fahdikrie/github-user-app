package com.dicoding.latihan.githubuser.activities


import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.latihan.githubuser.R
import com.dicoding.latihan.githubuser.adapters.UserListAdapter
import com.dicoding.latihan.githubuser.databinding.ActivityMainBinding
import com.dicoding.latihan.githubuser.models.User

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsers.setHasFixedSize(true)
        userList.addAll(userResList)
        showRecyclerList()

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUsers.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUsers.layoutManager = LinearLayoutManager(this)
        }
    }

    private val userResList: ArrayList<User>
        get() {
            val usernameRes = resources.getStringArray(R.array.username)
            val nameRes = resources.getStringArray(R.array.name)
            val locationRes = resources.getStringArray(R.array.location)
            val companyRes = resources.getStringArray(R.array.company)
            val repositoryRes = resources.getStringArray(R.array.repository)
            val followingRes = resources.getStringArray(R.array.following)
            val followersRes = resources.getStringArray(R.array.followers)

            /**
             * Having trouble getting the image to show,
             * so I use this reference & modified the xml:
             *
             * https://stackoverflow.com/questions/19791845/
             * reference-drawable-from-string-array-in-android/19792009
             */
            val avatarRes = resources.obtainTypedArray(R.array.avatar)

            val listHero = ArrayList<User>()
            for (i in usernameRes.indices) {
                val hero = User(
                    usernameRes[i],
                    nameRes[i],
                    locationRes[i],
                    companyRes[i],
                    repositoryRes[i],
                    followingRes[i],
                    followersRes[i],
                    avatarRes.getResourceId(i, 0),
                )
                listHero.add(hero)
            }
            return listHero
        }

    private fun showRecyclerList() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = UserListAdapter(userList)
        binding.rvUsers.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra("DATA", data)
                startActivity(intentToDetail)
            }
        })
    }
}