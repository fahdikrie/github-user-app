package com.dicoding.latihan.githubuser.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    var photo: String,
    var username: String,
    var name: String,
    var location: String,
    var company: String,
    var repositories: Int,
    var following: Int,
    var followers: Int,
) : Parcelable
