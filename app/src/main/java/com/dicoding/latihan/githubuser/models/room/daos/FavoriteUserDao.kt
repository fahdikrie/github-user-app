package com.dicoding.latihan.githubuser.models.room.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.latihan.githubuser.models.room.entities.FavoriteUserEntity

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteUser(favoriteUserEntity: FavoriteUserEntity)

    @Query("SELECT * from favorite_user ORDER BY id ASC")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUserEntity>>

    @Query("DELETE from favorite_user WHERE login = :login")
    suspend fun deleteFavoriteUser(login: String)
}