package com.deadshot.android.projectneostore.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductImage(
    @Json(name = "id") var id: Int,
    @Json(name = "product_id") var productId: Int,
    @Json(name = "image") var imageUrl: String,
    @Json(name = "created") var created: String,
    @Json(name = "modified") var modified: String
): Parcelable