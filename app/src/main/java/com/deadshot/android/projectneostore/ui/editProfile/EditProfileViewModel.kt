package com.deadshot.android.projectneostore.ui.editProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.UpdateUser
import com.deadshot.android.projectneostore.network.UpdateAccountApi
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.isEmailValid
import com.deadshot.android.projectneostore.utils.isNameValid
import com.deadshot.android.projectneostore.utils.isValidMobile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

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

    private val _checkUpdateSuccessful = MutableLiveData<Boolean>()
    val checkUpdateSuccessful: LiveData<Boolean>
        get() = _checkUpdateSuccessful

    var authListener: AuthListener? = null

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    fun onSubmit(){
        if (checkFieldsFilled())
            if (checkFieldsCorrect())
                updateUserData()
    }

    /**
     * Update User Data
     */
    private fun updateUserData() {

        coroutineScope.launch {
            val getPropertiesDeferred = UpdateAccountApi.retrofitService.updateAccount(
                access_token = access_token,
                first_name = first_name,
                last_name = last_name,
                email = email_id,
                phone_no = phone_number.toBigDecimal(),
                dob = dob,
                profile_pic = ""
            )
            try {
                /**
                 * - Calling await returns result from a network call when value is ready.
                 *  - await() : nonblocking, doesn't block UI
                 */
                val listResult = getPropertiesDeferred.await()
                if (listResult.status == 200) {
                    authListener?.onSuccess(listResult.user_msg)
                    _checkUpdateSuccessful.value =  true
                }else {
                    authListener?.onFailure("Error ${listResult.status} : ${listResult.user_msg}")
                }
            }catch (t: Throwable){
                Timber.i("Failure : ${t.message}")
            }
        }
    }

    /**
     * Check if fields are not empty
     */
    private fun checkFieldsFilled(): Boolean{
        when {
            first_name.isNullOrEmpty() -> {
                authListener?.onFailure("Please enter your First Name!")
                return false
            }
            last_name.isNullOrEmpty() -> {
                authListener?.onFailure("Please enter your Last Name")
                return false
            }
            email_id.isNullOrEmpty() -> {
                authListener?.onFailure("Please enter your Email")
                return false
            }
            phone_number.isNullOrEmpty() -> {
                authListener?.onFailure("Please enter your Phone Number")
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
                authListener?.onFailure("First Name Invalid")
                return false
            }
            !isNameValid(last_name) -> {
                authListener?.onFailure("Last Name Invalid")
                return false
            }
            !isEmailValid(email_id) -> {
                authListener?.onFailure("Email ID Invalid")
                return false
            }
            !isValidMobile(phone_number) -> {
                authListener?.onFailure("Invalid Phone Number")
                return false
            }
            else -> return true
        }
    }

    fun updateDone(){
        _checkUpdateSuccessful.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}