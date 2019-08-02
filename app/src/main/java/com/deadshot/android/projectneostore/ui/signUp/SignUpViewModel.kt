package com.deadshot.android.projectneostore.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.User
import com.deadshot.android.projectneostore.network.SignUpApi
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.isEmailValid
import com.deadshot.android.projectneostore.utils.isNameValid
import com.deadshot.android.projectneostore.utils.isPasswordValid
import com.deadshot.android.projectneostore.utils.isValidMobile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

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

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

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
        when {
            !isNameValid(firstName!!) -> {
                authListener?.onFailure("First name Invalid")
                return false
            }
            !isNameValid(lastName!!) -> {
                authListener?.onFailure("Last name Invalid")
                return false
            }
            !isEmailValid(emailId!!) -> {
                authListener?.onFailure("Email ID Invalid")
                return false
            }
            !isPasswordValid(password, confirmPassword) -> {
                authListener?.onFailure("Passwords do not match")
                return false
            }
            !isValidMobile(phoneNumber!!) -> {
                authListener?.onFailure("Invalid Phone Number")
                return false
            }
            else -> return true
        }
    }

    private fun onSignUp() {
        coroutineScope.launch {
            val getPropertiesDeferred = SignUpApi.retrofitService
                .doSignUp(firstName!!, lastName!!, emailId!!, password!!, confirmPassword!!, gender!!, phoneNumber!!.toBigDecimal())
            try {
                val listResult = getPropertiesDeferred.await()
                if (listResult.status == 200){
                    authListener?.onSuccess(listResult.user_msg)
                }else{
                    authListener?.onFailure("Error ${listResult.status} : ${listResult.user_msg}")
                }
            }catch (t: Throwable){
                authListener?.onFailure("Failure : ${t.message}")
                Timber.i("Failure : ${t.message}")
            }
        }
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

    fun signUpDone(){
        _signUpCheck.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
