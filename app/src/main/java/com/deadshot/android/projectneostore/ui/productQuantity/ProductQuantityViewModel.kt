package com.deadshot.android.projectneostore.ui.productQuantity

import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.ProductDetail
import com.deadshot.android.projectneostore.repository.AddToCartRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProductQuantityViewModel(val access_token: String, val productDetail: ProductDetail) : ViewModel() {

    private val addToCartRepository by lazy {
        AddToCartRepository(access_token = access_token)
    }

    private var _quantity = "1"

    val authListener = addToCartRepository.authListener

    val status = addToCartRepository.status

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun onSubmit(){
        coroutineScope.launch {
            addToCartRepository.addToCart(productId = productDetail.productId, quantity = _quantity)
        }
    }

    fun setQuantity(qty: String) {
        _quantity = qty
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