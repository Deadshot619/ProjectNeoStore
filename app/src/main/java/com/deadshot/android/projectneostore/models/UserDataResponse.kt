package com.deadshot.android.projectneostore.models

import java.util.*

data class UserDataResponse(
    var id: Int,
    var role_id: Int,
    var first_name: String,
    var last_name: String,
    var email: String,
    var username: String,
    var profile_pic: String?,
    var country_id: String?,
    var gender: String,
    var phone_no: Number,
    var dob: String?,
    var is_active: Boolean,
    var created: Date,
    var modified: Date,
    var access_token: String
)