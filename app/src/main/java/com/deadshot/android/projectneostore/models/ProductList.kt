package com.deadshot.android.projectneostore.models

import com.squareup.moshi.Json

/**
 * The property names of these data class are used by Moshi to match the names of values in JSON.
 */
data class ProductList(
    @Json(name = "id") val productId: Int,
    @Json(name = "product_category_id") val productCategoryId: Int,
    @Json(name = "name") val productName: String,
    val producer: String,
    @Json(name = "description") val productDescription: String,
    @Json(name = "cost") val price: Int,
    val rating: Int,
    @Json(name = "view_count") val viewCount: Int,
    val created: String,
    val modified: String,
    @Json(name = "product_images") val productImageUrl: String
)

data class ProductListResponse(
    @Json(name = "data") val products: List<ProductList>
): CommonResponse()