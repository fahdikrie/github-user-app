package com.dicoding.latihan.githubuser.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.latihan.githubuser.api.APIConfig
import com.dicoding.latihan.githubuser.models.responses.GithubUserFollow
import com.dicoding.latihan.githubuser.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {

    private val _followingList = MutableLiveData<List<GithubUserFollow>>()
    val followingList: LiveData<List<GithubUserFollow>> = _followingList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    @Suppress("UNCHECKED_CAST")
    fun getFollowing(username: String?) {
        _isLoading.value = true
        val client = APIConfig.getAPIServices().getDetailUserFollowingService(username)
        client.enqueue(object : Callback<List<GithubUserFollow>> {
            override fun onResponse(
                call: Call<List<GithubUserFollow>>,
                response: Response<List<GithubUserFollow>>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val followingListResponse = response.body()

                    if (followingListResponse.isNullOrEmpty()) {
                        _snackbarText.value = Event("Followings not found")
                    } else {
                        _followingList.value = followingListResponse
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _snackbarText.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<List<GithubUserFollow>>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}