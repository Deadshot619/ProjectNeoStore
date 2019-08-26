package com.deadshot.android.projectneostore.models

import com.squareup.moshi.Json

data class CommonResponse (
    var status: Int ,
    var message: String? ,
    @Json(name = "user_msg") var user_msg: String?
)