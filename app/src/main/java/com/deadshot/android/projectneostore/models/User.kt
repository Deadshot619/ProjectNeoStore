package com.deadshot.android.projectneostore.models

import com.google.gson.annotations.SerializedName

/**
 * User data while Login
 */
data class User(
    @SerializedName("status") var status: Int,
    @SerializedName("data") var data: UserDataResponse?,
    @SerializedName("message") var message: String,
    @SerializedName("user_msg") var user_msg: String
)

/**
 * User data class for updating user data
 */
data class UpdateUser(
    @SerializedName("status") var status: Int,
    @SerializedName("data") var userAccountData: AccountDataResponse,
    @SerializedName("message") var message: String,
    @SerializedName("user_msg") var user_msg: String
)

data class UserDetails(
    var status: Int,
    var data: AccountDataResponse,
    var message: String,
    var user_msg: String
)