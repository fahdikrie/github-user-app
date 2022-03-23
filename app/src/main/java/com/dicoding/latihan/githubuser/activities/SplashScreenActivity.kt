package com.dicoding.latihan.githubuser.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.latihan.githubuser.R
import com.dicoding.latihan.githubuser.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setHeader()
        animateLogo(binding)
        transitionToMain()
    }

    private fun setHeader() {
        supportActionBar?.hide()
    }

    /**
     * Reference:
     * https://stackoverflow.com/questions/35601042/
     * splash-screen-with-fade-in-animation-of-imageview
     */
    private fun animateLogo(binding: ActivitySplashScreenBinding) {
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding
            .imgSplashLogo
            .startAnimation(fadeIn)
    }

    /**
     * Reference:
     * https://www.geeksforgeeks.org/how-to-create-
     * an-animated-splash-screen-in-android/
     */
    private fun transitionToMain() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}