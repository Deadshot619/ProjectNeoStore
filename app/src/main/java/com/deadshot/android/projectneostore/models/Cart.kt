package com.deadshot.android.projectneostore.models

import com.squareup.moshi.Json

data class CartResponse(
    var status: Int,
    val data: Boolean,
    @Json(name = "total_carts") val totalCarts: Int?,
    var message: String?,
    @Json(name = "user_msg") var user_msg: String?
)