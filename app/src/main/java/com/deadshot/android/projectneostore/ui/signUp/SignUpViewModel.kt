package com.deadshot.android.projectneostore.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.User
import com.deadshot.android.projectneostore.network.SignUpApi
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.isEmailValid
import retrofit2.Call
import retrofit2.Response
import java.util.regex.Pattern

class SignUpViewModel : ViewModel(){

    var firstName: String? = null
    var lastName: String? = null
    var emailId: String? = null
    var password: String? = null
    var confirmPassword: String? = null
    var phoneNumber: String? = null
    var gender: String? = null
    var termsAndConditions: Boolean = false
    var authListener: AuthListener? = null

    private val _signUpCheck = MutableLiveData<Boolean>()
    val signUpCheck: LiveData<Boolean>
        get() = _signUpCheck

    fun onSignUpBtnClick(){
        if (checkFieldsFilled() && checkFieldsCorrect())
            onSignUp()
    }


    /**
     * Check if fields are not empty
     */
    private fun checkFieldsFilled(): Boolean{
        when {
            firstName.isNullOrEmpty() -> {
                authListener?.onFailure("Please enter First Name!")
                return false
            }
            lastName.isNullOrEmpty() -> {
                authListener?.onFailure("Please enter Last Name")
                return false
            }
            emailId.isNullOrEmpty() -> {
                authListener?.onFailure("Please enter Email")
                return false
            }
            password.isNullOrEmpty() || confirmPassword.isNullOrEmpty() -> {
                authListener?.onFailure("Passwords cannot be Empty")
                return false
            }
            gender.isNullOrEmpty() -> {
                authListener?.onFailure("Please select a Gender")
                return false
            }
            phoneNumber.isNullOrEmpty() -> {
                authListener?.onFailure("Please enter Phone Number")
                return false
            }
            !termsAndConditions -> {
                authListener?.onFailure("Please accept NeoSTORE's Terms & Conditions")
                return false
            }
            else -> return true
        }
    }

    /**
     * Check if fields are correctly filled
     */
    private fun checkFieldsCorrect(): Boolean{
        return if (!isEmailValid(emailId!!)) {
            authListener?.onFailure("Email ID Invalid")
            false
        }else if (!isPasswordValid(password, confirmPassword)){
            authListener?.onFailure("Passwords do not match")
            false
        }else if(!isValidMobile(phoneNumber!!)){
            authListener?.onFailure("Invalid Phone Number")
            false
        }else{
            true
        }
    }

    private fun onSignUp() {
//        authListener?.onFailure("Signup Done")
        SignUpApi.retrofitService
            .doSignUp(firstName!!, lastName!!, emailId!!, password!!, confirmPassword!!, gender!!, phoneNumber!!.toBigDecimal())
            .enqueue(object: retrofit2.Callback<User>{
                override fun onFailure(call: Call<User>, t: Throwable) {
                    _signUpCheck.value = false
                    authListener?.onFailure("Failed : " + t.message!!)
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful){
                        when {
                            response.body()!!.status == 200 -> {
                                authListener?.onFailure(response.body()!!.message)
                                authListener?.onFailure(response.body()!!.user_msg)
                            }
                            response.body()!!.status == 500 -> {
                                authListener?.onFailure(response.body()!!.message)
                                authListener?.onFailure(response.body()!!.user_msg)
                            }
                            response.body()!!.status == 404 -> {
                                authListener?.onFailure(response.body()!!.message)
                                authListener?.onFailure(response.body()!!.user_msg)
                            }
                            else -> {
                                authListener?.onSuccess()
                                authListener?.onFailure(response.body()!!.message)
                                authListener?.onFailure(response.body()!!.user_msg)
                            }
                        }
                    }
                }

            })
    }


    /**
     * Sets Gender to 'M'
     */
    fun onGenderMaleRadioBtnClick(){
        gender = "M"
    }

    /**
     * Sets Gender to 'F'
     */
    fun onGenderFemaleRadioBtnClick(){
        gender = "F"
    }

    /**
     * Sets Terms & Conditions
     */
    fun onTcClick(){
        termsAndConditions = !termsAndConditions
    }

    /**
     * Check if password is valid
     */
    private fun isPasswordValid(password: String?, confirmPassword: String?): Boolean {
        return password.equals(confirmPassword)
    }

    /**
     * Check if Mobile Number is valid
     */
    private fun isValidMobile(phone: String): Boolean{
            return !Pattern.matches("[a-zA-Z]+", phone) && phone.length in 7..13
    }
}
