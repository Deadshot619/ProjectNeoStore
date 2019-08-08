package com.deadshot.android.projectneostore.models

import java.util.*

data class ProductList(
    val id: Int,
    val product_category_id: Int,
    val name: String,
    val producer: String,
    val description: String,
    val cost: Double,
    val rating: Double,
    val view_count: Int,
    val created: Date,
    val modified: Date,
    val product_images: String
)

data class ProductListResponse(
    val status: Int,
    val products: List<ProductList>
)