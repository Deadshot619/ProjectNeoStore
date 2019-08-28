package com.deadshot.android.projectneostore.ui.productDetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.repository.ProductDetailRepository
import com.deadshot.android.projectneostore.utils.CHAIRS
import com.deadshot.android.projectneostore.utils.CUPBOARDS
import com.deadshot.android.projectneostore.utils.SOFAS
import com.deadshot.android.projectneostore.utils.TABLES
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val productId: Int, private val app: Application) : ViewModel() {

    /**
     * Lazily initialize ProductDetailRepository()
     */
    private val productDetailRepository by lazy {
        ProductDetailRepository(productId = productId)
    }

    /**
     * Status for Error checking
     */
    val status = productDetailRepository.status

    val properties = productDetailRepository.properties

    /**
     * Status of Rate Button
     */
    private val _rateButtonStatus = MutableLiveData<Boolean>()
    val rateButtonStatus: LiveData<Boolean>
        get() = _rateButtonStatus

    private val _buyNowButtonStatus = MutableLiveData<Boolean>()
    val buyNowButtonStatus: LiveData<Boolean>
        get() = _buyNowButtonStatus

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Call productDetailRepository.getProductDetail() on init so we can display status immediately.
     */
    init {
        coroutineScope.launch {
            productDetailRepository.getProductDetail()
        }
    }

    /**
     * Transform productCategoryId to show proper Product Category
     */
    val productCategory = Transformations.map(properties){
        app.applicationContext.getString(R.string.display_category_name,
            when (it?.productCategoryId){
                TABLES -> "Tables"
                CHAIRS -> "Chairs"
                SOFAS -> "Sofas"
                CUPBOARDS -> "Cupboards"
                else -> "Bed"
            }
        )
    }

    /**
     * Transform productPrice to show proper Product Price
     */
    val productPrice = Transformations.map(properties) {
        app.applicationContext.getString(R.string.display_price, it?.price)
    }

    /**
     * Changes value of [_rateButtonStatus] to true when Rate Button is clicked
     */
    fun onClickRateButton(){
        _rateButtonStatus.value = true
    }

    /**
     * Changes value of [_rateButtonStatus] to null when Rate Button is clicked
     */
    fun onClickRateButtonDone(){
        _rateButtonStatus.value = null
    }

    /**
    * Changes value of [_buyNowButtonStatus] to true when Buy Now Button is clicked
    */
    fun onClickBuyNowButton(){
        _buyNowButtonStatus.value = true
    }

    /**
     * Changes value of [_buyNowButtonStatus] to null when Buy Now Button is clicked
     */
    fun onClickBuyNowButtonDone(){
        _buyNowButtonStatus.value = null
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