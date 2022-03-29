package com.dicoding.latihan.githubuser.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.latihan.githubuser.activities.DetailActivity
import com.dicoding.latihan.githubuser.adapters.UserDetailFollowAdapter
import com.dicoding.latihan.githubuser.databinding.FragmentFollowingBinding
import com.dicoding.latihan.githubuser.models.responses.GithubUserFollow
import com.dicoding.latihan.githubuser.viewmodels.FollowingViewModel
import com.google.android.material.snackbar.Snackbar

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var username: String

    /**
     * Binding logics taken from:
     * https://developer.android.com/topic/
     * libraries/architecture/viewmodel#sharing
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = activity?.intent?.getStringExtra(DetailActivity.EXTRA_USERNAME)!!
        bindViewModelData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindViewModelData() {
        followingViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[FollowingViewModel::class.java]

        followingViewModel.getFollowing(username)

        followingViewModel.followingList.observe(viewLifecycleOwner) {
            showRecyclerList(it!!)
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        followingViewModel.snackbarText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    binding.frameLayoutFollowing,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showRecyclerList(users: List<GithubUserFollow>) {
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        val userListAdapter = UserDetailFollowAdapter(users)
        binding.rvFollowing.adapter = userListAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbFollowing.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}