package com.dicoding.latihan.githubuser.api

import com.dicoding.latihan.githubuser.models.responses.GithubUserSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIServices {
    @GET("/search/users")
    fun getUsersByQueryService(
        @Query("q") query: String?,
    ): Call<GithubUserSearchResponse>
}