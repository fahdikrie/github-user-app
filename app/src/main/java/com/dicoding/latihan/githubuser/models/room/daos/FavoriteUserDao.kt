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

    @Update
    suspend fun updateNews(favoriteUserEntity: FavoriteUserEntity)

    @Delete
    suspend fun deleteFavoriteUser(favoriteUserEntity: FavoriteUserEntity)

    @Query("SELECT EXISTS(SELECT * FROM favorite_user WHERE login = :login)")
    suspend fun isFavoriteUser(login: String): Boolean
}