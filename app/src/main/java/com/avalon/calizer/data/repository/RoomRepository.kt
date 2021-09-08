package com.avalon.calizer.data.repository


import com.avalon.calizer.data.local.*
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.local.follow.FollowingData
import com.avalon.calizer.data.local.follow.OldFollowersData
import com.avalon.calizer.data.local.follow.OldFollowingData
import com.avalon.calizer.data.local.profile.AccountsInfoData
import com.avalon.calizer.utils.MySharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class RoomRepository @Inject constructor(private val roomDao: RoomDao,private val prefs:MySharedPreferences) {


    suspend fun getSaveFollowingType(userId: Long):AccountsData{
        return roomDao.getSaveFollowingType(userId)
    }
    suspend fun getSaveFollowersType(userId: Long):AccountsData{
        return roomDao.getSaveFollowersType(userId)
    }


    suspend fun getAccountCookies(userId: Long):AccountsData{
        return roomDao.getUserCookies(userId)
    }


    suspend fun addAccount(accountsData: AccountsData) {
        roomDao.addAccount(accountsData)
    }

    fun getAccounts(): List<AccountsData> {
        return roomDao.getAllAccountDetails
    }
   suspend fun getFollowersData(position:Int):List<FollowersData>{
        return roomDao.getFollowersData(position,prefs.selectedAccount)
    }

    suspend fun getFollowingData(position:Int):List<FollowingData>{
        return roomDao.getFollowingData(position,prefs.selectedAccount)
    }

    suspend fun updateAccount(profile_Pic : String?, user_name:String?, ds_userId:String?){
        roomDao.updateAccountData(profile_Pic,user_name,ds_userId)
    }



//    suspend fun getNotFollow(): List<FollowData> {
//        return roomDao.getUnFollowers()
//    }


    suspend fun getUserInfo(): AccountsInfoData {
        return roomDao.getUserInfo(prefs.selectedAccount)
    }
    suspend fun addFollowersData(followersData: List<FollowersData>){
            roomDao.addFollowersData(followersData)
    }
    suspend fun addFollowingData(followingData:List<FollowingData> ){
        roomDao.addFollowingData(followingData)
    }
    suspend fun addOldFollowersData(oldFollowersData: List<OldFollowersData>){
        roomDao.addOldFollowersData(oldFollowersData)
    }
    suspend fun addOldFollowingData(oldFollowingData:List<OldFollowingData>){
        roomDao.addOldFollowingData(oldFollowingData)
    }

    suspend fun deleteFollowersData(){
        roomDao.deleteFollowersData(prefs.selectedAccount)
    }
    suspend fun deleteFollowingData(){
        roomDao.deleteFollowingData(prefs.selectedAccount)
    }
    suspend fun updateFollowersSaveType(){
        roomDao.updateFollowersSaveType(prefs.selectedAccount)
    }
    suspend fun updateFollowingSaveType(){
        roomDao.updateFollowingSaveType(prefs.selectedAccount)
    }
    suspend fun getSaveFollowingType():Boolean{
       return roomDao.getSaveFollowingType(prefs.selectedAccount).isFirstFollowingAnalyze
    }
    suspend fun getSaveFollowersType():Boolean{
       return roomDao.getSaveFollowersType(prefs.selectedAccount).isFirstFollowersAnalyze
    }
    /**
    suspend fun addLastFollowers(followersData: List<LastFollowersData>) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("Response", followersData.size.toString())
            roomDao.deleteLastFollowers()
            roomDao.lastAddFollowers(followersData)
        }
    }

    suspend fun addFollowing(followingData: List<FollowingData>) {
        CoroutineScope(Dispatchers.IO).launch {
            roomDao.addFollowing(followingData)
        }

    }

    suspend fun addLastFollowing(followingData: List<LastFollowingData>) {
        CoroutineScope(Dispatchers.IO).launch {
            roomDao.deleteLastFollowing()
            roomDao.lastAddFollowing(followingData)
        }
    }

    suspend fun getFollowers(): List<FollowData> {
        return roomDao.getFollowers()
    }

    suspend fun getFollowing(): List<FollowingData> {
        return roomDao.getFollowing()
    }
    **/
}