package com.deadshot.android.projectneostore.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deadshot.android.projectneostore.models.MyCartResponse
import com.deadshot.android.projectneostore.models.ProductsInfo
import com.deadshot.android.projectneostore.models.SingleProductInfo
import com.deadshot.android.projectneostore.network.MyCartApi
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.LoadingProductsStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MyCartRepository(private val access_token: String) {

    val authListener = MutableLiveData<AuthListener>()

    /**
     * Status for Error checking
     */
    private val _status = MutableLiveData<LoadingProductsStatus>()
    val status: LiveData<LoadingProductsStatus>
        get() = _status

    private val _properties = MutableLiveData<List<ProductsInfo>>()
    val propertiesProductInfo: LiveData<List<ProductsInfo>>
        get() = _properties

    private val _propertiesMyCartResponse = MutableLiveData<MyCartResponse>()
    val propertiesMyCartResponse: LiveData<MyCartResponse>
        get() = _propertiesMyCartResponse


    /**
     * Gets MyCart information from the [MyCartApi] Retrofit service and updates the
     * [SingleProductInfo] [LiveData]. The Retrofit service returns a coroutine Deferred, which we
     * await to get the result of the transaction.
     */
    suspend fun getCartDetails(){
        withContext(Dispatchers.Main){
            val getPropertiesDeferred = MyCartApi.retrofitService.getMyCartDetails(access_token = access_token)
            try {
                val listResult = getPropertiesDeferred.await()
                _status.value = LoadingProductsStatus.LOADING
                if (listResult.status == 200){
                    _propertiesMyCartResponse.value = listResult
                    _properties.value = listResult.productsInfo
                    Timber.i(listResult.productsInfo.toString())
                    _status.value = LoadingProductsStatus.DONE

                }else{
                    authListener.value?.onFailure("Error ${listResult.status} : ${listResult.user_msg}")
                    _status.value = LoadingProductsStatus.ERROR
                    _properties.value = null
                }
            }catch (t: Throwable){
                authListener.value?.onFailure("Failure : ${t.message}")
                Timber.i("Failure : ${t.message}")
            }
        }
    }
}