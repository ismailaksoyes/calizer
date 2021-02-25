package com.avalon.calizer.data.repository

import android.util.Log
import com.avalon.calizer.data.local.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class RoomRepository @Inject constructor(private val roomDao: RoomDao) {


    suspend fun addCookie(roomData: RoomData){
      roomDao.addCookie(roomData)
    }

     suspend fun getCookies():RoomData{
        return roomDao.readAllData()
    }

    suspend fun getNotFollow():List<FollowersData>{
        return roomDao.getUnFollowers()
    }

    suspend fun addFollowers(followersData:List<FollowersData>){
        CoroutineScope(Dispatchers.IO).launch {
            roomDao.addFollowers(followersData)
        }

    }
    suspend fun addLastFollowers(followersData: List<LastFollowersData>){
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("Response",followersData.size.toString())
            roomDao.deleteLastFollowers()
            roomDao.lastAddFollowers(followersData)
        }
    }
    suspend fun addFollowing(followingData: List<FollowingData>){
        CoroutineScope(Dispatchers.IO).launch {
            roomDao.addFollowing(followingData)
        }

    }
    suspend fun addLastFollowing(followingData: List<LastFollowingData>){
        CoroutineScope(Dispatchers.IO).launch {
            roomDao.deleteLastFollowing()
            roomDao.lastAddFollowing(followingData)
        }
    }
    suspend fun getFollowers(): List<FollowersData> {
        return roomDao.getFollowers()
    }
    suspend fun getFollowing():List<FollowingData>{
        return roomDao.getFollowing()
    }

}