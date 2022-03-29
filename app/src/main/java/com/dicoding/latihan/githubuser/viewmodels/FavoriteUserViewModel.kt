package com.dicoding.latihan.githubuser.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.latihan.githubuser.models.room.entities.FavoriteUserEntity
import com.dicoding.latihan.githubuser.models.room.repositories.FavoriteUserRepository
import kotlinx.coroutines.launch

class FavoriteUserViewModel(
    private val favoriteUserRepository: FavoriteUserRepository
) : ViewModel() {

    fun getAllFavoriteUsers() = favoriteUserRepository.getAllFavoriteUsers()

    fun setAsFavoriteUser(favoriteUserEntity: FavoriteUserEntity) {
        viewModelScope.launch {
            favoriteUserRepository.insertFavoriteUser(favoriteUserEntity)
        }
    }

    fun unsetFavoriteUser(login: String) {
        viewModelScope.launch {
            favoriteUserRepository.deleteFavoriteUser(login)
        }
    }
}
