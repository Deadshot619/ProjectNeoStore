package com.deadshot.android.projectneostore.ui.myAccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MyAccountModelFactory(
    private val firstName: String,
    private val lastName: String,
    private val email: String,
    private val phone: String,
    private val dob: String,
    accessToken: String
): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyAccountViewModel::class.java)){
            return MyAccountViewModel(
                firstName,
                lastName,
                email,
                phone,
                dob
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}