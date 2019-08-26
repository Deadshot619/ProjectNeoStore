package com.deadshot.android.projectneostore.ui.orderDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class OrderDetailModelFactory (private val accessToken: String): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderDetailViewModel::class.java)){
            return OrderDetailViewModel(accessToken) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}