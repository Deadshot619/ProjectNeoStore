package com.deadshot.android.projectneostore.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deadshot.android.projectneostore.models.UserDataResponse
import com.deadshot.android.projectneostore.network.LoginApi
import com.deadshot.android.projectneostore.ui.AuthListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class LoginRepository {

    var authListener = MutableLiveData<AuthListener?>()

    private val _userData = MutableLiveData<UserDataResponse>()
    val userData: LiveData<UserDataResponse>
        get() = _userData

    private val _loginCheck = MutableLiveData<Boolean>()
    val loginCheck: LiveData<Boolean>
        get() = _loginCheck

    /**
     * Check Progress bar status
     */
    private val _progressBarStatus = MutableLiveData<Boolean>()
    val progressBarStatus: LiveData<Boolean>
        get() = _progressBarStatus

    fun showProgressBar(){
        _progressBarStatus.value = true
    }

    fun hideProgressBar(){
        _progressBarStatus.value = false
    }

    suspend fun checkLogin(email: String?, password: String?){
        withContext(Dispatchers.Main){
            val getPropertiesDeferred = LoginApi.retrofitService.checkLogin(email!!, password!!)
            try {
                val listResult = getPropertiesDeferred.await()
                if (listResult.status == 200){
                    _userData.value = listResult.data
                    _loginCheck.value = true
                    authListener.value?.onSuccess(listResult.user_msg)
                    Timber.i(listResult.user_msg)
                }else{
                    authListener.value?.onFailure("Login Unsuccessful : ${listResult.user_msg}")
                    Timber.i("Error ${listResult.status} : ${listResult.message}")
                    hideProgressBar()
                }
            }catch (t: Throwable){
                authListener.value?.onFailure("Failure : ${t.message}")
                Timber.i("Failure : ${t.message}")
                hideProgressBar()
            }
        }
    }

    /**
     *  Change login listener value
     */
    fun loginDone(){
        _loginCheck.value = false
    }
}