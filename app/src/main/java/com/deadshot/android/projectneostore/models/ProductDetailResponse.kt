package com.deadshot.android.projectneostore.models

import com.squareup.moshi.Json

data class ProductDetailResponse(
    var status: Int,
    @Json(name = "data") var productDetail: ProductDetail,
    var message: String?,
    var user_msg: String?
)