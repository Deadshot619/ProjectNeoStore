package com.deadshot.android.projectneostore.ui.rateProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.ProductDetail
import com.deadshot.android.projectneostore.network.RateProductApi
import com.deadshot.android.projectneostore.repository.RateProductsRepository
import com.deadshot.android.projectneostore.ui.AuthListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class RateProductViewModel(val productDetail: ProductDetail) : ViewModel(){

    private val rateProductsRepository = RateProductsRepository(productDetail = productDetail)

    var authListener= rateProductsRepository.authListener

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val status = rateProductsRepository.status

    fun onClickRateBtn(){
        coroutineScope.launch {
            rateProductsRepository.rateProduct()
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