package com.deadshot.android.projectneostore.ui.rateProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deadshot.android.projectneostore.models.ProductDetail
import java.lang.IllegalArgumentException

class RateProductModelFactory(private val productDetail: ProductDetail) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RateProductViewModel::class.java)){
            return RateProductViewModel(productDetail) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}