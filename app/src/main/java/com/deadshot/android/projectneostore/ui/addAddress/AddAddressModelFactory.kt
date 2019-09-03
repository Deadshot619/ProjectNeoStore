package com.deadshot.android.projectneostore.ui.addAddress

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deadshot.android.projectneostore.database.AddressDatabaseDao

class AddAddressModelFactory(
    private val database: AddressDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddAddressViewModel::class.java)){
            return AddAddressViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}