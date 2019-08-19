package com.deadshot.android.projectneostore.ui.rateProduct

import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.ProductDetail
import com.deadshot.android.projectneostore.repository.RateProductsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RateProductViewModel(val productDetail: ProductDetail) : ViewModel(){

    private val rateProductsRepository = RateProductsRepository(productDetail = productDetail)

    var authListener= rateProductsRepository.authListener

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val status = rateProductsRepository.status

    fun onClickRateBtn(){
        coroutineScope.launch {
            rateProductsRepository.rateProduct(productDetail.rating.toInt())
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