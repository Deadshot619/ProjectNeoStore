package com.deadshot.android.projectneostore.ui.productDetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel;
import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.models.ProductDetail
import com.deadshot.android.projectneostore.network.ProductDetailApi
import com.deadshot.android.projectneostore.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class ProductDetailViewModel(private val productId: Int, private val app: Application) : ViewModel() {

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
     * Status of Rate Button
     */
    private val _rateButtonStatus = MutableLiveData<Boolean>()
    val rateButtonStatus: LiveData<Boolean>
        get() = _rateButtonStatus

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    /**
     * Call getProductDetailProperties() on init so we can display status immediately.
     */
    init {
        getProductDetailProperties()
    }

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

    val productPrice = Transformations.map(properties) {
        app.applicationContext.getString(R.string.display_price, it?.price)
    }

    /**
     * Gets Products information from the ProductList API Retrofit service and updates the
     * [ProductDetail] [LiveData]. The Retrofit service returns a coroutine Deferred, which we
     * await to get the result of the transaction.
     */
    private fun getProductDetailProperties() {
        coroutineScope.launch {
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
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}