package com.deadshot.android.projectneostore.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deadshot.android.projectneostore.models.ProductList
import com.deadshot.android.projectneostore.network.ProductListApi
import com.deadshot.android.projectneostore.utils.LoadingProductsStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ProductsRepository (private val productValue: Int) {

    /**
     * Status for Error checking
     */
    private val _status = MutableLiveData<LoadingProductsStatus>()
    val status: LiveData<LoadingProductsStatus>
        get() = _status

    private val _properties = MutableLiveData<List<ProductList>>()
    val properties: LiveData<List<ProductList>>
        get() = _properties

    /**
     * Gets Products information from the ProductList API Retrofit service and updates the
     * [ProductList] [List] [LiveData]. The Retrofit service returns a coroutine Deferred, which we
     * await to get the result of the transaction.
     */
    suspend fun loadProducts(){
        withContext(Dispatchers.Main){
            val getPropertiesDeferred = ProductListApi.retrofitService
                .getProductList(productCategoryId = (productValue.toString()), limit = 100, page = null)
            try {
                val listResult = getPropertiesDeferred.await()
                _status.value = LoadingProductsStatus.LOADING
                if (listResult.status == 200){
                    _status.value = LoadingProductsStatus.DONE
                    _properties.value = listResult.products
                }else{
                    _status.value = LoadingProductsStatus.ERROR
                }
            }catch (t: Throwable){
                Timber.i("Exception : ${t.message}\n${t.localizedMessage}")
                _status.value = LoadingProductsStatus.ERROR
//                _properties.value = ArrayList()
            }
        }
    }
}