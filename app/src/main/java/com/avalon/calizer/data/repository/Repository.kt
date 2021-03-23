package com.avalon.calizer.data.repository

import com.avalon.calizer.data.api.ApiHelper
import com.avalon.calizer.data.api.ApiClient
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollowers
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val apiHelper: ApiHelper) {


    suspend fun getReelsTray(cookies: String) = apiHelper.getReelsTray(cookies)

     suspend fun getUserFollowers(userId:String,maxId: String?,rnkToken:String?, cookies: String):Response<ApiResponseUserFollowers>{
        return ApiClient.API.getFollowers(userId,maxId,rnkToken,cookies)
    }
    suspend fun getUserFollowing(userId:String,maxId: String?,rnkToken:String?, cookies: String):Response<ApiResponseUserFollowers>{
        return ApiClient.API.getFollowing(userId,maxId,rnkToken,cookies)
    }

}