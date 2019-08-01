package com.deadshot.android.projectneostore.ui.editProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.UpdateUser
import com.deadshot.android.projectneostore.network.UpdateAccountApi
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.isEmailValid
import com.deadshot.android.projectneostore.utils.isValidMobile
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

    fun onSubmit(){
        if (checkFieldsFilled())
            if (checkFieldsCorrect())
                updateUserData()
    }

    /**
     * Update User Data
     */
    private fun updateUserData() {
        UpdateAccountApi.retrofitService.updateAccount(
            access_token = access_token,
            first_name = first_name,
            last_name = last_name,
            email = email_id,
            phone_no = phone_number.toBigDecimal(),
            dob = dob,
            profile_pic = ""
        ).enqueue(object : retrofit2.Callback<UpdateUser>{
            override fun onFailure(call: Call<UpdateUser>, t: Throwable) {
                authListener?.onFailure("Failed : ${t.message}")
                authListener?.onFailure("Failed : ${t.message}")
            }

            override fun onResponse(call: Call<UpdateUser>, response: Response<UpdateUser>) {
                if (response.isSuccessful){
                    when{
                        response.body()!!.status == 200 ->{
                            authListener?.onSuccess(response.body()!!.user_msg)
                            _checkUpdateSuccessful.value =  true
                        }
                        else -> {
                        authListener?.onFailure(response.body()!!.user_msg)
                        }
                    }
                }else{
                    Timber.i("Error ${response.code()} : ${response.message()}")
                }
            }

        })
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
        return if (!isEmailValid(email_id)) {
            authListener?.onFailure("Email ID Invalid")
            false
        }else if(!isValidMobile(phone_number)){
            authListener?.onFailure("Invalid Phone Number")
            false
        }else{
            true
        }
    }

    fun updateDone(){
        _checkUpdateSuccessful.value = false
    }
}