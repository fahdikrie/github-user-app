package com.dicoding.latihan.githubuser.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.latihan.githubuser.api.APIConfig
import com.dicoding.latihan.githubuser.models.responses.GithubUser
import com.dicoding.latihan.githubuser.models.responses.GithubUserSearchResponse
import com.dicoding.latihan.githubuser.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _userList = MutableLiveData<List<GithubUser>>()
    val userList: LiveData<List<GithubUser>> = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    @Suppress("UNCHECKED_CAST")
    fun getUsers(query: String?) {
        _isLoading.value = true
        val client = APIConfig.getAPIServices().getUsersByQueryService(query)
        client.enqueue(object : Callback<GithubUserSearchResponse> {
            override fun onResponse(
                call: Call<GithubUserSearchResponse>,
                response: Response<GithubUserSearchResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val userListResponse = response.body()?.items

                    if (userListResponse.isNullOrEmpty()) {
                        _snackbarText.value = Event("User not found")
                    } else {
                        _userList.value = userListResponse as List<GithubUser>
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _snackbarText.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<GithubUserSearchResponse>, t: Throwable) {
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