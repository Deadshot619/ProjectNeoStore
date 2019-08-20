package com.deadshot.android.projectneostore.models

import com.squareup.moshi.Json

data class SingleProductInfo(
    @Json(name = "cost") val productPrice: Int,
    @Json(name = "id") val productId: Int,
    @Json(name = "name") val productName: String,
    @Json(name = "product_category") val productCategory: String,
    @Json(name = "product_images") val productImages: String,
    @Json(name = "sub_total") val subTotal: Int
)

data class ProductsInfo(
    val id: Int,
    @Json(name = "product_id") val productId: Int,
    val quantity: Int,
    val product: SingleProductInfo
)

data class MyCartResponse(
    val status: Int,
    @Json(name = "data") val productsInfo: List<ProductsInfo>?,
    val count: Int?,
    val total: Float?,
    val message: String?,
    val user_msg: String?
)
