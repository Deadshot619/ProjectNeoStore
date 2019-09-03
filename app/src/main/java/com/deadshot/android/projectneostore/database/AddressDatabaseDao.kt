package com.deadshot.android.projectneostore.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import retrofit2.http.DELETE

@Dao
interface AddressDatabaseDao {

    @Insert
    fun insert(address: Address)

    @Query("SELECT * FROM address_table WHERE addressId = :key")
    fun get(key: Long): Address

    @Query("SELECT * FROM address_table ORDER BY addressId DESC")
    fun getAllAddress(): LiveData<List<Address>>

    @Delete
    fun delete(address: Address)
}