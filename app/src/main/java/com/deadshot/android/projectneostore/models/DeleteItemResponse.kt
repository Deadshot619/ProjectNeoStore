package com.deadshot.android.projectneostore.models

import com.squareup.moshi.Json

data class DeleteItemResponse(
    val data: Boolean?,
    val message: String,
    val status: Int,
    @Json(name = "total_carts") val totalCarts: Int?,
    val user_msg: String
)