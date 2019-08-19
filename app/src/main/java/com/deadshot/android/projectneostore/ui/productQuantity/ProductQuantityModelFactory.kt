package com.deadshot.android.projectneostore.ui.productQuantity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deadshot.android.projectneostore.models.ProductDetail
import java.lang.IllegalArgumentException

class ProductQuantityModelFactory (private val productDetail: ProductDetail) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductQuantityViewModel::class.java)){
            return ProductQuantityViewModel(productDetail) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}