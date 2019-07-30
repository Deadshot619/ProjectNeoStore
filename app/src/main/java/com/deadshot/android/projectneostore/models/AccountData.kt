package com.deadshot.android.projectneostore.models

data class AccountDataResponse (
    var user_data: UserDataResponse,
    var product_categories: List<Products>,
    var total_carts: Int,
    var total_orders: Int
)