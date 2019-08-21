package com.deadshot.android.projectneostore.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deadshot.android.projectneostore.models.MyCartResponse
import com.deadshot.android.projectneostore.models.ProductsInfo
import com.deadshot.android.projectneostore.models.SingleProductInfo
import com.deadshot.android.projectneostore.network.DeleteItemApi
import com.deadshot.android.projectneostore.network.MyCartApi
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.EnumCart
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

    /**
     * Status to check whether cart is empty
     */
    private val _cartStatus = MutableLiveData<EnumCart>()
    val cartStatus: LiveData<EnumCart>
        get() = _cartStatus


    private val _properties = MutableLiveData<List<ProductsInfo>>()
    val propertiesProductInfo: LiveData<List<ProductsInfo>>
        get() = _properties

    private val _propertiesMyCartResponse = MutableLiveData<MyCartResponse>()
    val propertiesMyCartResponse: LiveData<MyCartResponse>
        get() = _propertiesMyCartResponse


    private val _reloadCartStatus = MutableLiveData<Boolean>()
    val reloadCartStatus: LiveData<Boolean>
        get() = _reloadCartStatus


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
                    if (listResult.productsInfo.isNullOrEmpty()){
                        _cartStatus.value = EnumCart.CARTEMPTY
                    }else {
                        _propertiesMyCartResponse.value = listResult
                        _properties.value = listResult.productsInfo
                        Timber.i(listResult.productsInfo.toString())
                        _cartStatus.value = EnumCart.CARTNOTEMPTY
                    }
                    _status.value = LoadingProductsStatus.DONE
                    //this function stops reloading the page
                    deleteItemfailed()
                }else{
                    authListener.value?.onFailure("Error ${listResult.status} : ${listResult.user_msg}")
                    _status.value = LoadingProductsStatus.ERROR
                    _properties.value = null
                    //this function stops reloading the page
                    deleteItemfailed()
                }
            }catch (t: Throwable){
                _status.value = LoadingProductsStatus.ERROR
                authListener.value?.onFailure("Failure : ${t.message}")
                Timber.i("Failure : ${t.message}")
                //this function stops reloading the page
                deleteItemfailed()
            }
        }
    }

    /**
     * Deletes a item from cart from the [DeleteItemApi] Retrofit service and updates the
     * [] [LiveData]. The Retrofit service returns a coroutine Deferred, which we
     * await to get the result of the transaction.
     */
    suspend fun deleteFromCart(productId: Int){
        withContext(Dispatchers.Main){
            val getPropertiesDeferred = DeleteItemApi.retrofitService
                .deleteItemFromCart(access_token = access_token, productId = productId.toString())
            try {
                val listResult = getPropertiesDeferred.await()
                if (listResult.status == 200){
                    authListener.value?.onSuccess("${listResult.user_msg}")
                    deleteItemDone()
                }else{
                    authListener.value?.onFailure("Error ${listResult.status} : ${listResult.user_msg}")
                    deleteItemfailed()
                }
            }catch (t: Throwable){
                authListener.value?.onFailure("Failure : ${t.message}")
                Timber.i("Failure : ${t.message}")
                deleteItemfailed()
            }
        }
    }

    /**
     * Function to reload the page
     */
    fun deleteItemDone() {
        _reloadCartStatus.value = true
    }

    /**
     * Function to stop reloading the page
     */
    fun deleteItemfailed() {
        _reloadCartStatus.value = null
    }
}