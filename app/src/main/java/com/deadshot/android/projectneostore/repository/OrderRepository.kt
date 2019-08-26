package com.deadshot.android.projectneostore.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deadshot.android.projectneostore.network.OrderNowApi
import com.deadshot.android.projectneostore.ui.AuthListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class OrderRepository (private val access_token: String){
    val authListener = MutableLiveData<AuthListener>()

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    suspend fun placeOrder(access_token: String, address: String){
        withContext(Dispatchers.Main){
            val getPropertiesDeferred = OrderNowApi.retrofitService
                .orderNow(access_token = access_token, address = address)

            try {
                val listResult = getPropertiesDeferred.await()
                if (listResult.status == 200){
                    authListener.value?.onSuccess("${listResult.user_msg}")
                    _status.value = true
                }else{
                    authListener.value?.onFailure("Error ${listResult.status} : ${listResult.user_msg}")
                    _status.value = false
                }
            }catch (t: Throwable){
                authListener.value?.onFailure("Failure : ${t.message}")
                Timber.i("Failure : ${t.message}")
                _status.value = false
            }
        }
    }
}