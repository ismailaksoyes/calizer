package com.avalon.avalon.data.remote.insresponse


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class User(
    @SerializedName("account_badges")
    val accountBadges: List<Any>,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("has_anonymous_profile_picture")
    val hasAnonymousProfilePicture: Boolean,
    @SerializedName("is_private")
    val isPrivate: Boolean,
    @SerializedName("is_verified")
    val isVerified: Boolean,
    @SerializedName("latest_reel_media")
    val latestReelMedia: Int,
    @SerializedName("pk")
    val pk: Long,
    @SerializedName("profile_pic_url")
    val profilePicUrl: String,
    @SerializedName("profile_pic_id")
    val profilePicİd: String,
    @SerializedName("story_reel_media_ids")
    val storyReelMediaİds: List<Any>,
    @SerializedName("username")
    val username: String
)