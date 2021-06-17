package com.avalon.calizer.ui.main.fragments.analyze.followanalyze

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.avalon.calizer.R
import com.avalon.calizer.data.local.FollowData
import com.avalon.calizer.databinding.FragmentAllFollowersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllFollowersFragment : Fragment() {
    private lateinit var binding: FragmentAllFollowersBinding

    @Inject
    lateinit var viewModel: FollowViewModel
    private val followsAdapter by lazy { FollowsAdapter() }


    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()

        lifecycleScope.launchWhenStarted {
            viewModel.followers.collectLatest {
            //  followsAdapter.submitData(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.followers.collectLatest {
               Log.d("DataCollect","${it.map { data-> data.fullName.toString() }}")
                followsAdapter.submitData(it)
            }
        }
        binding.rcFollowData.setOnClickListener {
            val followData = FollowData(username = "AGFDFGSDFSD")
            viewModel.followers

        }
        lifecycleScope.launch {
            followsAdapter.loadStateFlow.collectLatest {loadStates ->
                binding.pbFollowData.isVisible = loadStates.refresh is LoadState.Loading
            }
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllFollowersBinding.inflate(inflater,container,false)
        return binding.root
    }
    private fun setupRecyclerview() {
        binding.rcFollowData.adapter = followsAdapter
        binding.rcFollowData.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )

    }


}