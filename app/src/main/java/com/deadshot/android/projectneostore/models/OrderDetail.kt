package com.deadshot.android.projectneostore.models

import com.squareup.moshi.Json

data class ProductsInOrder(
    val id: Int,
    @Json(name = "order_id") val orderId: Int,
    @Json(name = "prod_cat_name") val productCategory: String,
    @Json(name = "prod_image") val productImage: String,
    @Json(name = "prod_name") val productName: String,
    @Json(name = "product_id")val productId: Int,
    val quantity: Int,
    val total: Int
)

data class OrderDetail(
    @Json(name = "cost") val price: Double ,
    val address: String ,
    val id: Int ,
    @Json(name = "order_details") val ProductsInOrder: List<ProductsInOrder>
)

data class OrderDetailResponse(
    val status: Int,
    @Json(name = "data") val orderDetail: OrderDetail?,
    val message: String?,
    val user_msg: String?
)