package com.dicoding.latihan.githubuser.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.latihan.githubuser.R
import com.dicoding.latihan.githubuser.databinding.ActivitySplashScreenBinding
import com.dicoding.latihan.githubuser.models.datastore.preferences.SettingPreference
import com.dicoding.latihan.githubuser.viewmodels.SettingsViewModel
import com.dicoding.latihan.githubuser.viewmodels.factories.SettingsViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setHeader()
        setTheme()
        animateLogo(binding)
        transitionToMain()
    }

    private fun setTheme() {
        val pref = SettingPreference.getInstance(dataStore)
        val settingsViewModel = ViewModelProvider(this,
            SettingsViewModelFactory(pref)
        )[SettingsViewModel::class.java]

        settingsViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })
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