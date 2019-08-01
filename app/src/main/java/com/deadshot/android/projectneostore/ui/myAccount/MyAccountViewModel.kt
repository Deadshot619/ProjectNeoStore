package com.deadshot.android.projectneostore.ui.myAccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.ui.AuthListener

class MyAccountViewModel(
    firstName: String,
    lastName: String,
    email: String,
    phone: String,
    dob: String
) : ViewModel(){

    val first_name: String = firstName
    val last_name: String = lastName
    val email_id: String = email
    val phone_number: String = phone
    val dob: String = dob
    var authListener: AuthListener? = null

    private val _navigateEditProfileCheck = MutableLiveData<Boolean>()
    val navigateEditProfileCheck: LiveData<Boolean>
        get() = _navigateEditProfileCheck

    private val _navigateResetPasswordCheck = MutableLiveData<Boolean>()
    val navigateResetPasswordCheck: LiveData<Boolean>
        get() = _navigateResetPasswordCheck

    fun onClickEditProfile(){
        _navigateEditProfileCheck.value = true
    }

    fun onClickResetPassword(){
        _navigateResetPasswordCheck.value = true
    }

    fun navigateEditProfileDone() {
        _navigateEditProfileCheck.value = false
    }

    fun navigateResetPasswordDone() {
        _navigateResetPasswordCheck.value = false
    }

}