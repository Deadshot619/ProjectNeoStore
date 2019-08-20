package com.deadshot.android.projectneostore.ui.myCart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MyCartModelFactory(private val access_token: String): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyCartViewModel::class.java)){
            return MyCartViewModel(access_token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}