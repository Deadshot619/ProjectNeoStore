package com.deadshot.android.projectneostore.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deadshot.android.projectneostore.models.ProductDetail
import com.deadshot.android.projectneostore.network.ProductDetailApi
import com.deadshot.android.projectneostore.utils.LoadingProductsStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ProductDetailRepository(private val productId: Int) {
    /**
     * Status for Error checking
     */
    private val _status = MutableLiveData<LoadingProductsStatus>()
    val status: LiveData<LoadingProductsStatus>
        get() = _status

    private val _properties = MutableLiveData<ProductDetail>()
    val properties: LiveData<ProductDetail>
        get() = _properties

    /**
     * Gets Products information from the ProductList API Retrofit service and updates the
     * [ProductDetail] [LiveData]. The Retrofit service returns a coroutine Deferred, which we
     * await to get the result of the transaction.
     */
    suspend fun getProductDetail(){
        withContext(Dispatchers.Main){
            val getPropertiesDeferred =
                ProductDetailApi.retrofitService.getProductDetail(productId = productId)
            try {
                val listResult = getPropertiesDeferred.await()
                _status.value = LoadingProductsStatus.LOADING
                if (listResult.status == 200){
                    _properties.value = listResult.productDetail
                    Timber.i(listResult.productDetail.toString())
                    _status.value = LoadingProductsStatus.DONE
                }else{
                    Timber.i("Error ${listResult.status} : ${listResult.message}")
                    _status.value = LoadingProductsStatus.ERROR
                    _properties.value = null
                }
            }catch (t: Throwable){
                Timber.i("Exception : ${t.message}\n${t.localizedMessage}")
                _status.value = LoadingProductsStatus.ERROR
                _properties.value = null
            }
        }
    }
}