package com.deadshot.android.projectneostore.ui.signUp

import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.repository.SignUpRepository
import com.deadshot.android.projectneostore.utils.isEmailValid
import com.deadshot.android.projectneostore.utils.isNameValid
import com.deadshot.android.projectneostore.utils.isPasswordValid
import com.deadshot.android.projectneostore.utils.isValidMobile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    var firstName: String? = null
    var lastName: String? = null
    var emailId: String? = null
    var password: String? = null
    var confirmPassword: String? = null
    var phoneNumber: String? = null
    var gender: String? = null
    var termsAndConditions: Boolean = false

    private val signUpRepository by lazy {
        SignUpRepository()
    }

    var authListener = signUpRepository.authListener

    val signUpCheck = signUpRepository.signUpCheck

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun onSignUpBtnClick(){
        if (checkFieldsFilled() && checkFieldsCorrect()) {
            coroutineScope.launch {
                signUpRepository.onSignUp(
                    firstName = firstName,
                    lastName = lastName,
                    emailId = emailId,
                    password = password,
                    confirmPassword = confirmPassword,
                    gender = gender,
                    phoneNumber = phoneNumber
                )
            }
        }
    }


    /**
     * Check if fields are not empty
     */
    private fun checkFieldsFilled(): Boolean{
        when {
            firstName.isNullOrEmpty() -> {
                authListener.value?.onFailure("Please enter First Name!")
                return false
            }
            lastName.isNullOrEmpty() -> {
                authListener.value?.onFailure("Please enter Last Name")
                return false
            }
            emailId.isNullOrEmpty() -> {
                authListener.value?.onFailure("Please enter Email")
                return false
            }
            password.isNullOrEmpty() || confirmPassword.isNullOrEmpty() -> {
                authListener.value?.onFailure("Passwords cannot be Empty")
                return false
            }
            gender.isNullOrEmpty() -> {
                authListener.value?.onFailure("Please select a Gender")
                return false
            }
            phoneNumber.isNullOrEmpty() -> {
                authListener.value?.onFailure("Please enter Phone Number")
                return false
            }
            !termsAndConditions -> {
                authListener.value?.onFailure("Please accept NeoSTORE's Terms & Conditions")
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
                authListener.value?.onFailure("First name Invalid")
                return false
            }
            !isNameValid(lastName!!) -> {
                authListener.value?.onFailure("Last name Invalid")
                return false
            }
            !isEmailValid(emailId!!) -> {
                authListener.value?.onFailure("Email ID Invalid")
                return false
            }
            !isPasswordValid(password, confirmPassword) -> {
                authListener.value?.onFailure("Passwords do not match")
                return false
            }
            !isValidMobile(phoneNumber!!) -> {
                authListener.value?.onFailure("Invalid Phone Number")
                return false
            }
            else -> return true
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
        signUpRepository.signUpDone()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
