package com.deadshot.android.projectneostore.ui.myOrders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyOrdersModelFactory(private val accessToken: String): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyOrdersViewModel::class.java)){
            return MyOrdersViewModel(accessToken) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}