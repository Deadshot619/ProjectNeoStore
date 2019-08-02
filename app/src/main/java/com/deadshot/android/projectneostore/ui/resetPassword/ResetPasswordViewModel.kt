package com.deadshot.android.projectneostore.ui.resetPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.network.ResetPasswordApi
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.isPasswordContainCharacter
import com.deadshot.android.projectneostore.utils.isPasswordValid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class ResetPasswordViewModel(private val accessToken: String) : ViewModel(){
    var oldPassword = ""
    var newPassword = ""
    var confirmPassword = ""
    var authListener: AuthListener? = null

    private val _checkResetPasswordSuccessful = MutableLiveData<Boolean>()
    val checkResetPasswordSuccessful: LiveData<Boolean>
        get() = _checkResetPasswordSuccessful

    /**
     * Set Job & coroutine scope
     */
    private var viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun onClickSave(){
        if (checkFieldsFilled() && checkFieldsCorrect() && checkCharacters()){
            resetPassword()
        }
    }

    private fun resetPassword() {
        coroutineScope.launch {
            val getPropertiesDeferred = ResetPasswordApi.retrofitService.makePasswordReset(
                access_token = accessToken,
                old_password = oldPassword,
                new_password = newPassword,
                confirm_password = confirmPassword
            )

            try {
                val listResult = getPropertiesDeferred.await()
                when {
                    listResult.status == 200 -> {
                        authListener?.onSuccess(listResult.user_msg)
                        _checkResetPasswordSuccessful.value =  true
                    }
                    listResult.status == 500 || listResult.status == 400 || listResult.status == 404 -> authListener?.onFailure("Error ${listResult.status} : ${listResult.user_msg}")
                    else -> authListener?.onFailure("Error ${listResult.status} : ${listResult.user_msg}")
                }
            }catch (t: Throwable){
                authListener?.onFailure("Failure : ${t.message}")
                Timber.i("Failure : ${t.message}")
            }
        }
    }

    fun resetDone(){
        _checkResetPasswordSuccessful.value = false
    }

    /**
     * Check if fields contains correct characters
     */
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}