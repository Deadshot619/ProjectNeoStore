package com.deadshot.android.projectneostore.models

import com.squareup.moshi.Json

data class OrderList(
    @Json(name ="cost") val price: Int ,
    val created: String ,
    val id: Int
)

data class OrderListResponse(
    @Json(name = "data") val orderList: List<OrderList>?
): CommonResponse()