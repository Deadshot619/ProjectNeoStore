package com.deadshot.android.projectneostore.models

data class User(
    var status: Int,
    var data: UserDataResponse,
    var message: String,
    var user_msg: String
)