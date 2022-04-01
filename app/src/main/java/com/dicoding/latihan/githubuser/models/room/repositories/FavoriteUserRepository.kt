package com.dicoding.latihan.githubuser.models.room.repositories

import androidx.lifecycle.LiveData
import com.dicoding.latihan.githubuser.models.room.daos.FavoriteUserDao
import com.dicoding.latihan.githubuser.models.room.entities.FavoriteUserEntity

class FavoriteUserRepository private constructor(
    private val favoriteUserDao: FavoriteUserDao
) {
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUserEntity>> {
        return favoriteUserDao.getAllFavoriteUsers()
    }

    suspend fun insertFavoriteUser(favoriteUserEntity: FavoriteUserEntity) {
        favoriteUserDao.insertFavoriteUser(favoriteUserEntity)
    }

    suspend fun deleteFavoriteUser(login: String) {
        favoriteUserDao.deleteFavoriteUser(login)
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