package com.deadshot.android.projectneostore.ui.addressList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deadshot.android.projectneostore.database.AddressDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddressListViewModel(
    val database: AddressDatabaseDao ,
    application: Application
) : AndroidViewModel(application){

    private val _navigateToAddAddress = MutableLiveData<Boolean>()
    val navigateToAddAddress: LiveData<Boolean>
        get() = _navigateToAddAddress

    val viewModelJob = Job()
    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var addresses = database.getAllAddress()

    init {
        initializeAddresses()
    }

    private fun initializeAddresses() {
        coroutineScope.launch {

        }
    }


    fun navigateToAddAddress(){
        _navigateToAddAddress.value = true
    }

    fun navigateToAddAddressDone(){
        _navigateToAddAddress.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}