package com.deadshot.android.projectneostore.ui.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ProductDetailModelFactory(private val productId: Int) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)){
            return ProductDetailViewModel(productId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}