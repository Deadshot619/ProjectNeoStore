package com.deadshot.android.projectneostore.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_table")
data class Address(
    @PrimaryKey(autoGenerate = true)
    val addressId: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "address")
    val address: String = "",

    @ColumnInfo(name = "apartment")
    val apartment: String = "",

    @ColumnInfo(name = "country")
    val country: String = "",

    @ColumnInfo(name = "state")
    val state: String = "",

    @ColumnInfo(name = "city")
    val city: String = "",

    @ColumnInfo(name = "zip_code")
    val zipCode: String = ""
)