package com.deadshot.android.projectneostore.ui.addressList

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deadshot.android.projectneostore.database.AddressDatabaseDao

class AddressListModelFactory(
    private val database: AddressDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddressListViewModel::class.java)){
            return AddressListViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}