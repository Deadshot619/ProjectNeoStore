package com.deadshot.android.projectneostore.ui.productDetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProductDetailModelFactory(private val productId: Int, private val app: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)){
            return ProductDetailViewModel(productId, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}