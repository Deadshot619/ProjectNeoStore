package com.deadshot.android.projectneostore.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deadshot.android.projectneostore.network.ResetPasswordApi
import com.deadshot.android.projectneostore.ui.AuthListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ResetPasswordRepository (private val accessToken: String){

    val authListener = MutableLiveData<AuthListener?>()

    private val _checkResetPasswordSuccessful = MutableLiveData<Boolean>()
    val checkResetPasswordSuccessful: LiveData<Boolean>
        get() = _checkResetPasswordSuccessful

    suspend fun resetPassword(oldPassword: String, newPassword: String, confirmPassword: String) {
        withContext(Dispatchers.Main) {
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
                        authListener.value?.onSuccess(listResult.user_msg)
                        _checkResetPasswordSuccessful.value =  true
                    }
                    listResult.status == 500 || listResult.status == 400 || listResult.status == 404 -> authListener.value?.onFailure("Error ${listResult.status} : ${listResult.user_msg}")
                    else -> authListener.value?.onFailure("Error ${listResult.status} : ${listResult.user_msg}")
                }
            }catch (t: Throwable){
                authListener.value?.onFailure("Failure : ${t.message}")
                Timber.i("Failure : ${t.message}")
            }
        }
    }

    fun resetDone(){
        _checkResetPasswordSuccessful.value = false
    }
}