package com.dicoding.latihan.githubuser.models.room.injections

import android.content.Context
import com.dicoding.latihan.githubuser.models.room.databases.FavoriteUserDatabase
import com.dicoding.latihan.githubuser.models.room.repositories.FavoriteUserRepository

object FavoriteUserInjection {
    fun provideRepository(context: Context): FavoriteUserRepository {
        val database = FavoriteUserDatabase.getInstance(context)
        val dao = database.favoriteUserDao()
        return FavoriteUserRepository.getInstance(dao)
    }
}