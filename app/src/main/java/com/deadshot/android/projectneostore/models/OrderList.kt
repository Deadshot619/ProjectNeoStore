package com.deadshot.android.projectneostore.models

import com.squareup.moshi.Json

data class OrderList(
    @Json(name ="cost") val price: Int ,
    val created: String ,
    val id: Int
)

data class OrderListResponse(
    val status: Int,
    @Json(name = "data") val orderList: List<OrderList>?,
    val message: String?,
    val user_msg: String?
)