package com.deadshot.android.projectneostore.ui.tables

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.ProductList
import com.deadshot.android.projectneostore.models.ProductListResponse
import com.deadshot.android.projectneostore.network.ProductListApi
import com.deadshot.android.projectneostore.network.ProductListApiService
import com.deadshot.android.projectneostore.utils.LoadingProductsStatus
import com.deadshot.android.projectneostore.utils.TABLES
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class TablesViewModel : ViewModel(){

    private val _status = MutableLiveData<LoadingProductsStatus>()
    val status: LiveData<LoadingProductsStatus>
        get() = _status

    private val _properties = MutableLiveData<List<ProductList>>()
    val properties: LiveData<List<ProductList>>
        get() = _properties

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getProductListProperties()
    }

    /**
     * Gets Products information from the ProductList API Retrofit service and updates the
     * [ProductList] [List] [LiveData]. The Retrofit service returns a coroutine Deferred, which we
     * await to get the result of the transaction.
     */
    private fun getProductListProperties(){
        coroutineScope.launch{
            val getPropertiesDeferred = ProductListApi.retrofitService
                .getProductList(productCategoryId = (TABLES.toString()), limit = null, page = null)
            try {
                val listResult = getPropertiesDeferred.await()
                _status.value = LoadingProductsStatus.LOADING
                if (listResult.status == 200){
                    _status.value = LoadingProductsStatus.DONE
                    _properties.value = listResult.products
                }else{
                    _status.value = LoadingProductsStatus.DONE
                }
            }catch (t: Throwable){
                Timber.i("Exception : ${t.message}\n${t.localizedMessage}")
                _status.value = LoadingProductsStatus.ERROR
//                _properties.value = ArrayList()
            }
        }
    }

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}