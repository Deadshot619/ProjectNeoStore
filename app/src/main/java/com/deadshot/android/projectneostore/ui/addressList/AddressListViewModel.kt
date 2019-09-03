package com.deadshot.android.projectneostore.ui.addressList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class AddressListViewModel : ViewModel(){

    private val _navigateToAddAddress = MutableLiveData<Boolean>()
    val navigateToAddAddress: LiveData<Boolean>
        get() = _navigateToAddAddress

    val viewModelJob = Job()
    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

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