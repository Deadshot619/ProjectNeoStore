package com.deadshot.android.projectneostore.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deadshot.android.projectneostore.models.ProductDetail
import com.deadshot.android.projectneostore.network.RateProductApi
import com.deadshot.android.projectneostore.ui.AuthListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class RateProductsRepository (private val productDetail: ProductDetail){
    var authListener = MutableLiveData<AuthListener?>()

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    suspend fun rateProduct(rating: Int){
        withContext(Dispatchers.Main){
            val getPropertiesDeferred =
                RateProductApi.retrofitService.setProductRating(product_id = productDetail.productId, rating = rating)
            try {
                val listResult = getPropertiesDeferred.await()
                if (listResult.status == 200){
                    _status.value = true
                    authListener.value?.onSuccess("${listResult.user_msg} : $rating")
                }else{
                    _status.value = null
                    authListener.value?.onFailure("Error ${listResult.status} : ${listResult.user_msg}")
                }
            }catch (t: Throwable){
                _status.value = null
                authListener.value?.onFailure("Failure : ${t.message}")
                Timber.i("Failure : ${t.message}")
            }
        }
    }
}