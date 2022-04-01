package com.dicoding.latihan.githubuser.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.dicoding.latihan.githubuser.models.datastore.preferences.SettingPreference
import com.dicoding.latihan.githubuser.viewmodels.SettingsViewModel

class SettingsViewModelFactory(private val pref: SettingPreference) : NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
