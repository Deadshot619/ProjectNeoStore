package com.deadshot.android.projectneostore.models

import java.util.*

data class Products(
    var id: Int,
    var name: String,
    var icon_image: String,
    var created: Date,
    var modified: Date
)