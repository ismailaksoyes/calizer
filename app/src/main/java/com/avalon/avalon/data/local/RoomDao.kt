package com.avalon.avalon.data.local

import androidx.room.*

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCookie(roomData: RoomData)

    @Query("SELECT * FROM cookie_table WHERE cookieId=1")
    suspend fun readAllData(): RoomData

    @Query("DELETE FROM followers_table")
    suspend fun deleteData()

    @Query("DELETE FROM last_followers_table")
    suspend fun deleteData1()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFollowers(followersData:List<FollowersData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFollowing(followingData: FollowingData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun lastAddFollowers(lastFollowersData:List<LastFollowersData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun lastAddFollowing(lastFollowingData: List<LastFollowingData>)

    @Query("SELECT * FROM followers_table")
    suspend fun getFollowers(): List<FollowersData>


    @Query("SELECT * FROM following_table")
    suspend fun getFollowing(): List<FollowingData>

    @Query("SELECT * FROM followers_table EXCEPT SELECT * FROM following_table")
    suspend fun getNotFollow(): List<FollowersData>
}