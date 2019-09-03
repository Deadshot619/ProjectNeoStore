package com.deadshot.android.projectneostore.ui.addAddress

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.database.AddressDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class AddAddressViewModel(
    database: AddressDatabaseDao ,
    application: Application
) : AndroidViewModel(application){
    var address: String = ""
    var apartment: String = ""
    var country: String = ""
    var state: String = ""
    var city: String = ""
    var zipCode: String = ""


    val viewModelJob = Job()
    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}