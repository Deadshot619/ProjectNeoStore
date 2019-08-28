package com.deadshot.android.projectneostore.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProductsModelFactory (private val productValue: Int): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsViewModel::class.java)){
            return ProductsViewModel(productValue) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}