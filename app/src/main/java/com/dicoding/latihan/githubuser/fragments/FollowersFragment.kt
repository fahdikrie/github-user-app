package com.dicoding.latihan.githubuser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.latihan.githubuser.activities.DetailActivity
import com.dicoding.latihan.githubuser.adapters.UserDetailFollowAdapter
import com.dicoding.latihan.githubuser.databinding.FragmentFollowersBinding
import com.dicoding.latihan.githubuser.models.responses.GithubUserFollow
import com.dicoding.latihan.githubuser.viewmodels.FollowersViewModel
import com.google.android.material.snackbar.Snackbar

class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var followersViewModel: FollowersViewModel
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
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
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
        followersViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[FollowersViewModel::class.java]

        followersViewModel.getFollowers(username)

        followersViewModel.followersList.observe(viewLifecycleOwner) {
            showRecyclerList(it!!)
        }

        followersViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        followersViewModel.snackbarText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    binding.frameLayoutFollowers,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showRecyclerList(users: List<GithubUserFollow>) {
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        val userListAdapter = UserDetailFollowAdapter(users)
        binding.rvFollowers.adapter = userListAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbFollowers.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}