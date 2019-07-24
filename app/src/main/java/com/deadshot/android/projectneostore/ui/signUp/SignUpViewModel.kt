package com.deadshot.android.projectneostore.ui.signUp

import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.isEmailValid

class SignUpViewModel : ViewModel(){

    var firstName: String? = null
    var lastName: String? = null
    var emailId: String? = null
    var password: String? = null
    var confirmPassword: String? = null
    var phoneNumber: String? = null
    var gender: Boolean = true
    var termsAndConditions: Boolean = false
    var authListener: AuthListener? = null

    fun onSignUpBtnClick(){
        if (!isEmailValid(emailId!!)) {
            authListener?.onFailure("Invalid EmailId")
            return
        }
        if (!isPasswordValid(password, confirmPassword)){
            authListener?.onFailure("Passwords do not match")
            return
        }
    }

    private fun isPasswordValid(password: String?, confirmPassword: String?): Boolean {
        return password.equals(confirmPassword)
    }
}