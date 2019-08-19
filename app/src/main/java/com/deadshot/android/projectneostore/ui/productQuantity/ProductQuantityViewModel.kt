package com.deadshot.android.projectneostore.ui.productQuantity

import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.ProductDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ProductQuantityViewModel(val productDetail: ProductDetail) : ViewModel() {

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
    * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
    * Retrofit service to stop.
    */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}