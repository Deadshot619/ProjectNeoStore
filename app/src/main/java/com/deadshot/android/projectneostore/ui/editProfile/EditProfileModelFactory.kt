package com.deadshot.android.projectneostore.ui.editProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class EditProfileModelFactory : ViewModelProvider.Factory{
    override fun <T: ViewModel?> create(modelClass: Class<T>): T{
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)){
            return EditProfileViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}