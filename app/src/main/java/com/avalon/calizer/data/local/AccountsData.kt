package com.avalon.calizer.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "accounts_table",indices = [Index(value = arrayOf("dsUserID") ,unique = true)])
data class AccountsData(
    @PrimaryKey(autoGenerate = true)
    var uid:Long?=null,
    var userName:String= "",
    var profilePic:String = "",
    var csfr: String = "",
    var dsUserID: Long = -1L,
    var rur: String = "",
    var sessID: String = "",
    var shbid: String = "",
    var shbts: String = "",
    var mid: String = "",
    var allCookie:String = "",
    var isFirstFollowersAnalyze:Boolean = true,
    var isFirstFollowingAnalyze:Boolean = true,

)
