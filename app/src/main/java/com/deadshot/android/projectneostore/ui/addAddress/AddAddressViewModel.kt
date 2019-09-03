package com.deadshot.android.projectneostore.ui.addAddress

import android.app.Application
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.database.AddressDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class AddAddressViewModel(
    database: AddressDatabaseDao ,
    application: Application
) : ViewModel(){
    val viewModelJob = Job()
    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}