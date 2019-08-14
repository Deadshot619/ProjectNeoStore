package com.deadshot.android.projectneostore.ui.rateProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.ProductDetail
import com.deadshot.android.projectneostore.network.RateProductApi
import com.deadshot.android.projectneostore.ui.AuthListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class RateProductViewModel(val productDetail: ProductDetail) : ViewModel(){

    var authListener: AuthListener? = null

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    fun onClickRateBtn(){
        coroutineScope.launch {
            val getPropertiesDeferred =
                RateProductApi.retrofitService.setProductRating(product_id = productDetail.productId, rating = productDetail.rating)
            try {
                val listResult = getPropertiesDeferred.await()
                if (listResult.status == 200){
                    _status.value = true
                    authListener?.onSuccess("${listResult.user_msg}")
                }else{
                    _status.value = null
                    authListener?.onFailure("Error ${listResult.status} : ${listResult.user_msg}")
                }
            }catch (t: Throwable){
                _status.value = null
                authListener?.onFailure("Failure : ${t.message}")
                Timber.i("Failure : ${t.message}")
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