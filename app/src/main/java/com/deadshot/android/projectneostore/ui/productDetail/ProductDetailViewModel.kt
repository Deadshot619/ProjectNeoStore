package com.deadshot.android.projectneostore.ui.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.deadshot.android.projectneostore.models.ProductDetail
import com.deadshot.android.projectneostore.models.ProductList
import com.deadshot.android.projectneostore.utils.LoadingProductsStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ProductDetailViewModel(productId: Int) : ViewModel() {

    /**
     * Status for Error checking
     */
    private val _status = MutableLiveData<LoadingProductsStatus>()
    val status: LiveData<LoadingProductsStatus>
        get() = _status

    private val _properties = MutableLiveData<ProductDetail>()
    val properties: LiveData<ProductDetail>
        get() = _properties



    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    /**
     * Call getProductDetailProperties() on init so we can display status immediately.
     */
    init {
        getProductDetailProperties()
    }

    private fun getProductDetailProperties() {

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