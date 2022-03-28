package com.dicoding.latihan.githubuser.api

import com.dicoding.latihan.githubuser.models.responses.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIServices {
    @GET("/search/users")
    fun getUsersByQueryService(
        @Query("q") query: String?,
    ): Call<GithubUserSearchResponse>

    @GET("users/{username}")
    fun getDetailUserService(
        @Path("username")
        username: String?
    ): Call<GithubUserDetailResponse>

    @GET("users/{username}/followers")
    fun getDetailUserFollowersService(
        @Path("username")
        username: String?
    ): Call<GithubUserFollowResponse>

    @GET("users/{username}/following")
    fun getDetailUserFollowingService(
        @Path("username")
        username: String?
    ): Call<GithubUserFollowResponse>
}