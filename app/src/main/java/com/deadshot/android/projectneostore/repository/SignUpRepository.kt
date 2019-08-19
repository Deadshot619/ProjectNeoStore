package com.deadshot.android.projectneostore.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deadshot.android.projectneostore.network.SignUpApi
import com.deadshot.android.projectneostore.ui.AuthListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class SignUpRepository (){
    var authListener = MutableLiveData<AuthListener?>()

    private val _signUpCheck = MutableLiveData<Boolean>()
    val signUpCheck: LiveData<Boolean>
        get() = _signUpCheck

    suspend fun onSignUp(
        firstName: String?,
        lastName: String?,
        emailId: String?,
        password: String?,
        confirmPassword: String?,
        gender: String?,
        phoneNumber: String?
    ) {
        withContext(Dispatchers.Main) {
            val getPropertiesDeferred = SignUpApi.retrofitService
                .doSignUp(firstName!!, lastName!!, emailId!!, password!!, confirmPassword!!, gender!!, phoneNumber!!.toBigDecimal())
            try {
                val listResult = getPropertiesDeferred.await()
                if (listResult.status == 200){
                    authListener.value?.onSuccess(listResult.user_msg)
                }else{
                    authListener.value?.onFailure("Error ${listResult.status} : ${listResult.user_msg}")
                }
            }catch (t: Throwable){
                authListener.value?.onFailure("Failure : ${t.message}")
                Timber.i("Failure : ${t.message}")
            }
        }
    }

    fun signUpDone(){
        _signUpCheck.value = false
    }
}