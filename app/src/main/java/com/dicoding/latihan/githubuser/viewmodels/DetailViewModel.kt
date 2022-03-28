package com.dicoding.latihan.githubuser.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.latihan.githubuser.api.APIConfig
import com.dicoding.latihan.githubuser.models.responses.GithubUserDetailResponse
import com.dicoding.latihan.githubuser.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _userDetail = MutableLiveData<GithubUserDetailResponse>()
    val userDetail: LiveData<GithubUserDetailResponse> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    fun getUser(username: String) {
        _isLoading.value = true
        val client = APIConfig.getAPIServices().getDetailUserService(username)
        client.enqueue(object : Callback<GithubUserDetailResponse> {
            override fun onResponse(
                call: Call<GithubUserDetailResponse>,
                response: Response<GithubUserDetailResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _snackbarText.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<GithubUserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}