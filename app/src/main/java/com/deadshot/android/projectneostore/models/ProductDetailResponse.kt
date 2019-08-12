package com.deadshot.android.projectneostore.models

data class ProductDetailResponse(
    var status: Int,
    var data: ProductDetail,
    var message: String,
    var user_msg: String
)