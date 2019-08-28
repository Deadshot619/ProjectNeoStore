package com.deadshot.android.projectneostore.ui.editProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditProfileModelFactory(
    private val firstName: String,
    private val lastName: String,
    private val email: String,
    private val phone: String,
    private val dob: String?,
    private val access_token: String
) : ViewModelProvider.Factory{
    override fun <T: ViewModel?> create(modelClass: Class<T>): T{
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)){
            return EditProfileViewModel(
                firstName,
                lastName,
                email,
                phone,
                dob,
                access_token
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}