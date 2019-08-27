package com.deadshot.android.projectneostore.models

import com.squareup.moshi.Json

 open class CommonResponse (
    var status: Int? = null,
    var message: String? = null,
    @Json(name = "user_msg") var user_msg: String?= null
 )