package com.deadshot.android.projectneostore.ui.tables

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.ProductList
import com.deadshot.android.projectneostore.models.ProductListResponse
import com.deadshot.android.projectneostore.network.ProductListApi
import com.deadshot.android.projectneostore.network.ProductListApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class TablesViewModel : ViewModel(){

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    private val _property = MutableLiveData<ProductListResponse>()
    val property: LiveData<ProductListResponse>
        get() = _property

    private val _properties = MutableLiveData<List<ProductList>>()
    val properties: LiveData<List<ProductList>>
        get() = _properties

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getProductListProperties()
    }

    private fun getProductListProperties(){
        coroutineScope.launch{
            var getPropertiesDeferred = ProductListApi.retrofitService
                .getProductList(productCategoryId = (2.toString()), limit = null, page = null)
            try {
                var listResult = getPropertiesDeferred.await()
                if (listResult.status == 200){
                    _property.value = listResult
                    _properties.value = listResult.products
                    Timber.i(listResult.products[0].productName)
                }
                Timber.i(listResult.toString())
            }catch (t: Throwable){
                Timber.i("Exception : ${t.message}\n${t.localizedMessage}")
            }
        }
    }
}