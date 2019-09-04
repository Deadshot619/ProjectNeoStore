package com.deadshot.android.projectneostore.ui.addAddress

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.database.Address
import com.deadshot.android.projectneostore.database.AddressDatabaseDao
import kotlinx.coroutines.*

class AddAddressViewModel(
    val database: AddressDatabaseDao ,
    application: Application
) : AndroidViewModel(application){
    var name: String = ""
    var address: String = ""
    var apartment: String = ""
    var country: String = ""
    var state: String = ""
    var city: String = ""
    var zipCode: String = ""

    var addressModel = MutableLiveData<Address>()

    val viewModelJob = Job()
    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun onClickAddAddress(){
        coroutineScope.launch {
            addAddress()
        }
    }

    private suspend fun addAddress(){
        withContext(Dispatchers.IO){
            database.insert(Address(address = address, apartment = apartment, country = country, state = state, city = city, zipCode = zipCode))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}