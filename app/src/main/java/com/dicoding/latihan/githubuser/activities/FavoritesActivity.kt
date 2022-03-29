package com.dicoding.latihan.githubuser.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.latihan.githubuser.databinding.ActivityMainBinding

class FavoritesActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}