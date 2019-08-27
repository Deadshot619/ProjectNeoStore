package com.deadshot.android.projectneostore.models

import com.squareup.moshi.Json

data class CartResponse(
    val data: Boolean,
    @Json(name = "total_carts") val totalCarts: Int?
): CommonResponse()