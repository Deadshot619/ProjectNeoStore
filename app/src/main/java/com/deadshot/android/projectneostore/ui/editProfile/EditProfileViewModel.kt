package com.deadshot.android.projectneostore.ui.editProfile

import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.repository.EditProfileRepository
import com.deadshot.android.projectneostore.utils.isEmailValid
import com.deadshot.android.projectneostore.utils.isNameValid
import com.deadshot.android.projectneostore.utils.isValidMobile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EditProfileViewModel(
    firstName: String,
    lastName: String,
    email: String,
    phone: String,
    dob: String?,
    access_token: String
) : ViewModel(){

    var access_token: String = access_token
    var first_name: String = firstName
    var last_name: String = lastName
    var email_id: String = email
    var phone_number: String = phone
    var dob: String = dob.toString()

    private val editProfileRepository by lazy {
        EditProfileRepository(access_token)
    }

    val checkUpdateSuccessful = editProfileRepository.checkUpdateSuccessful
    var authListener= editProfileRepository.authListener

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    fun onSubmit(){
        if (checkFieldsFilled())
            if (checkFieldsCorrect())
                coroutineScope.launch {
                    editProfileRepository.updateUserData(
                        first_name, last_name, email_id, phone_number, dob
                    )
                }
    }

    /**
     * Check if fields are not empty
     */
    private fun checkFieldsFilled(): Boolean{
        when {
            first_name.isNullOrEmpty() -> {
                authListener.value?.onFailure("Please enter your First Name!")
                return false
            }
            last_name.isNullOrEmpty() -> {
                authListener.value?.onFailure("Please enter your Last Name")
                return false
            }
            email_id.isNullOrEmpty() -> {
                authListener.value?.onFailure("Please enter your Email")
                return false
            }
            phone_number.isNullOrEmpty() -> {
                authListener.value?.onFailure("Please enter your Phone Number")
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
            !isNameValid(first_name) -> {
                authListener.value?.onFailure("First Name Invalid")
                return false
            }
            !isNameValid(last_name) -> {
                authListener.value?.onFailure("Last Name Invalid")
                return false
            }
            !isEmailValid(email_id) -> {
                authListener.value?.onFailure("Email ID Invalid")
                return false
            }
            !isValidMobile(phone_number) -> {
                authListener.value?.onFailure("Invalid Phone Number")
                return false
            }
            else -> return true
        }
    }

    fun updateDone(){
        editProfileRepository.updateDone()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}