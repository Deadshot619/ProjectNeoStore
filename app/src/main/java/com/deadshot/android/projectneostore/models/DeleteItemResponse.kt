package com.deadshot.android.projectneostore.models

import com.squareup.moshi.Json

data class DeleteItemResponse(
    val data: Boolean?,
    @Json(name = "total_carts") val totalCarts: Int?
): CommonResponse()