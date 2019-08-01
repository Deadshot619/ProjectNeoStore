package com.deadshot.android.projectneostore.ui.resetPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ResetPasswordModelFactory(
    private val access_token: String
): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResetPasswordViewModel::class.java)){
            return ResetPasswordViewModel(
                access_token
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}