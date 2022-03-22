package com.dicoding.latihan.githubuser.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    var username: String,
    var name: String,
    var location: String,
    var company: String,
    var repositories: String,
    var following: String,
    var followers: String,
    var avatar: Int,
) : Parcelable
