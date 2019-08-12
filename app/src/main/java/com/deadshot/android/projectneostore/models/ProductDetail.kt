package com.deadshot.android.projectneostore.models

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.utils.TABLES
import com.squareup.moshi.Json

data class ProductDetail(
    @Json(name = "cost") var price: Int,
    var created: String,
    var description: String,
    @Json(name = "id") var productId: Int,
    var modified: String,
    @Json(name = "name") var productName: String,
    var producer: String,
    @Json(name = "product_category_id") var productCategoryId: Int,
    var rating: Int,
    @Json(name = "view_count") var viewCount: Int,
    @Json(name = "product_images") var productImages: List<ProductImage>
)
