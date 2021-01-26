package com.avalon.avalon.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.avalon.avalon.data.local.RoomDao
import com.avalon.avalon.data.local.MyDatabase
import com.avalon.avalon.data.repository.RoomRepository
import com.avalon.avalon.data.repository.Repository
import com.avalon.avalon.databinding.ActivityMainBinding
import com.avalon.avalon.PREFERENCES
import com.avalon.avalon.R
import com.avalon.avalon.data.local.FollowersData
import com.avalon.avalon.data.local.LastFollowersData
import com.avalon.avalon.data.remote.insresponse.ApiResponseUserFollowers
import com.avalon.avalon.ui.main.fragments.MainViewPagerAdapter
import com.avalon.avalon.ui.main.fragments.analyze.AnalyzeFragment
import com.avalon.avalon.ui.main.fragments.profile.ProfileFragment
import com.avalon.avalon.ui.main.fragments.settings.SettingsFragment
import com.avalon.avalon.utils.Utils
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var cookies: String
    private lateinit var profileFragment: ProfileFragment
    private lateinit var analyzeFragment: AnalyzeFragment
    private lateinit var settingsFragment: SettingsFragment
    private lateinit var prevMenuItem:MenuItem
    private lateinit var viewPager: ViewPager
    private lateinit var adapter:MainViewPagerAdapter
    private val followersList = ArrayList<FollowersData>()
    private val followersLastList = ArrayList<LastFollowersData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPager = binding.viewPager
        binding.bottomNavigation.itemIconTintList = null

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item->
            when(item.itemId){

                R.id.profile->{
                    viewPager.currentItem = 0
                    true
                }
                R.id.analyze->{
                    viewPager.currentItem = 1
                    true
                }
                R.id.settings->{
                    viewPager.currentItem = 2
                    true
                }
                else ->{
                    false
                }

            }
        }
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

                binding.bottomNavigation.menu.getItem(position).isChecked = true
                prevMenuItem = binding.bottomNavigation.menu.getItem(position);
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
        setupViewPager(binding.viewPager);
        val dao: RoomDao = MyDatabase.getInstance(application).roomDao
        val dbRepository = RoomRepository(dao)
        val repository = Repository()
        val factory = MainViewModelFactory(dbRepository, repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        PREFERENCES.firstLogin = true
        if (!PREFERENCES.allCookie.isNullOrEmpty()) {
            cookies = PREFERENCES.allCookie!!
            if (Utils.getTimeStatus(PREFERENCES.followersUpdateDate)) {

              //  getFollowersList(userId = "19748713375")

               // PREFERENCES.followersUpdateDate = System.currentTimeMillis()
            }
        }

        viewModel.allFollowers.observe(this, Observer { response ->
            if (response.isSuccessful) {
                if (!response.body()?.nextMaxId.isNullOrEmpty()) {

                    getFollowersList(
                        maxId = response.body()?.nextMaxId,
                        userId = "19748713375"
                    )

                } else {
                    Log.d("Response", "null max id")

                    followersData()
                }

                setRoomFollowers(response.body())
            }
        })

        viewModel.noFollow.observe(this, Observer { response ->
            Log.d("Response", "" + response.username)

        })


    }

    fun setupViewPager(viewPager :ViewPager){
    adapter = MainViewPagerAdapter(supportFragmentManager)
    profileFragment = ProfileFragment()
        analyzeFragment = AnalyzeFragment()
        settingsFragment = SettingsFragment()
        adapter.AddFragment(profileFragment)
        adapter.AddFragment(analyzeFragment)
        adapter.AddFragment(settingsFragment)
        viewPager.adapter = adapter
    }
    private fun followersData() {
        if (PREFERENCES.firstLogin) {

            Log.d("Response", "list-> " + followersList.size.toString())
            Log.d("Response", "listlast-> " + followersLastList.size.toString())

            viewModel.getNotFollow()
            viewModel.addFollowers(followersList)
            viewModel.addLastFollowers(followersLastList)

            PREFERENCES.firstLogin = false

        } else {
            Log.d("Response", "listlast-> " + followersLastList.size.toString())
            viewModel.addLastFollowers(followersLastList)

        }
    }

    private fun setRoomFollowers(followersData: ApiResponseUserFollowers?) {

        if (followersData != null) {

            for (data in followersData.users) {
                val newList = LastFollowersData()
                val oldList = FollowersData()
                newList.pk = data.pk
                oldList.pk = data.pk
                newList.fullName = data.fullName
                oldList.fullName = data.fullName
                newList.profilePicUrl = data.profilePicUrl
                oldList.profilePicUrl = data.profilePicUrl
                newList.hasAnonymousProfilePicture = data.hasAnonymousProfilePicture
                oldList.hasAnonymousProfilePicture = data.hasAnonymousProfilePicture
                newList.isPrivate = data.isPrivate
                oldList.isPrivate = data.isPrivate
                newList.isVerified = data.isVerified
                oldList.isVerified = data.isVerified
                newList.username = data.username
                oldList.username = data.username
                followersLastList.add(newList)
                followersList.add(oldList)

            }


        }

    }

    fun getFollowersList(maxId: String? = null, userId: String): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            delay((500 + (0..250).random()).toLong())
            if (!maxId.isNullOrEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.getUserFollowers(
                        userId = userId,
                        maxId = maxId,
                        rnkToken = Utils.generateUUID(),
                        cookies
                    )


                }

            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.getUserFollowers(
                        userId = userId,
                        cookies = cookies,
                        maxId = null,
                        rnkToken = null
                    )

                }

            }
        }


    }

    fun getFollowingList(maxId: String? = null, userId: String): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            delay((500 + (0..250).random()).toLong())
            if (!maxId.isNullOrEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.getUserFollowers(
                        userId = userId,
                        maxId = maxId,
                        rnkToken = Utils.generateUUID(),
                        cookies
                    )


                }

            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.getUserFollowers(
                        userId = userId,
                        cookies = cookies,
                        maxId = null,
                        rnkToken = null
                    )

                }

            }
        }


    }


}


