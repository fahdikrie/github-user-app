package com.dicoding.latihan.githubuser.models.room.repositories

import androidx.lifecycle.LiveData
import com.dicoding.latihan.githubuser.models.room.daos.FavoriteUserDao
import com.dicoding.latihan.githubuser.models.room.entities.FavoriteUserEntity

class FavoriteUserRepository private constructor(
    private val favoriteUserDao: FavoriteUserDao
) {
    fun getFavoriteUsers(): LiveData<List<FavoriteUserEntity>> {
        return favoriteUserDao.getAllFavoriteUsers()
    }

    suspend fun setAsFavoriteUser(favoriteUserEntity: FavoriteUserEntity) {
        favoriteUserDao.insertFavoriteUser(favoriteUserEntity)
    }

    suspend fun unsetFavoriteUser(favoriteUserEntity: FavoriteUserEntity) {
        favoriteUserDao.deleteFavoriteUser(favoriteUserEntity)
    }

    suspend fun checkIfFavoriteUser(login: String) {
        favoriteUserDao.isFavoriteUser(login)
    }

    companion object {
        @Volatile
        private var instance: FavoriteUserRepository? = null
        fun getInstance(
            favoriteUserDao: FavoriteUserDao
        ): FavoriteUserRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteUserRepository(favoriteUserDao)
            }.also { instance = it }
    }
}