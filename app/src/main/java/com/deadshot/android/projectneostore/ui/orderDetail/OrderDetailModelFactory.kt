package com.deadshot.android.projectneostore.ui.orderDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OrderDetailModelFactory (private val accessToken: String, private val orderId: Int): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderDetailViewModel::class.java)){
            return OrderDetailViewModel(accessToken, orderId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}