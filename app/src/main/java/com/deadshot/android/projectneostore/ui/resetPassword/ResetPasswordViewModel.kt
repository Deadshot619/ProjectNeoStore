package com.deadshot.android.projectneostore.ui.resetPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.User
import com.deadshot.android.projectneostore.network.AccessToken.access_token
import com.deadshot.android.projectneostore.network.ResetPasswordApi
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.isPasswordContainCharacter
import com.deadshot.android.projectneostore.utils.isPasswordValid
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ResetPasswordViewModel(val accessToken: String) : ViewModel(){
    var oldPassword = ""
    var newPassword = ""
    var confirmPassword = ""
    var authListener: AuthListener? = null

    private val _checkResetPasswordSuccessful = MutableLiveData<Boolean>()
    val checkResetPasswordSuccessful: LiveData<Boolean>
        get() = _checkResetPasswordSuccessful


    fun onClickSave(){
        if (checkFieldsFilled() && checkFieldsCorrect() && checkCharacters()){
            resetPassword()
        }
    }

    private fun resetPassword() {
        ResetPasswordApi.retrofitService.makePasswordReset(
            access_token = accessToken,
            old_password = oldPassword,
            new_password = newPassword,
            confirm_password = confirmPassword
        ).enqueue(object: Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {
                authListener?.onFailure("Failed : ${t.message}")
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful){
                    when{
                        response.body()!!.status == 200 ->{
                            authListener?.onSuccess(response.body()!!.user_msg)
                            _checkResetPasswordSuccessful.value =  true
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

    fun resetDone(){
        _checkResetPasswordSuccessful.value = false
    }

    private fun checkCharacters(): Boolean{
        return when{
            !isPasswordContainCharacter(oldPassword) || !isPasswordContainCharacter(newPassword) || !isPasswordContainCharacter(confirmPassword) -> {
                authListener?.onFailure("Passwords should contain only Alphanumeric Characters")
                false
            }
            else -> true
        }
    }

    /**
     * Check if fields are not empty
     */
    private fun checkFieldsFilled(): Boolean{
        return when {
            oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty() -> {
                authListener?.onFailure("Password Fields cannot be empty")
                false
            }
            else -> true
        }
    }

    /**
     * Check if fields filled correctly
     */
    private fun checkFieldsCorrect(): Boolean{
        return when{
            isPasswordValid(oldPassword, newPassword) -> {
                authListener?.onFailure("Old Password & New Password cannot be same!")
                false
            }
            !isPasswordValid(newPassword, confirmPassword) -> {
                authListener?.onFailure("Passwords do not match!")
                false
            }
            else -> true
        }
    }
}