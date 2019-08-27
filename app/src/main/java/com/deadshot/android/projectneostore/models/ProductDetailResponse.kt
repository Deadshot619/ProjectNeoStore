package com.deadshot.android.projectneostore.models

import com.squareup.moshi.Json

data class ProductDetailResponse(
    @Json(name = "data") var productDetail: ProductDetail
): CommonResponse()