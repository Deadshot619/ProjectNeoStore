package com.deadshot.android.projectneostore.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.User
import com.deadshot.android.projectneostore.models.UserDataResponse
import com.deadshot.android.projectneostore.network.LoginApi
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.isEmailValid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class LoginViewModel : ViewModel(){

    var email: String? = null
    var password: String? = null
    var authListener: AuthListener? = null
    private val _userData = MutableLiveData<UserDataResponse>()
    val userData: LiveData<UserDataResponse>
        get() = _userData

    private val _loginCheck = MutableLiveData<Boolean>()
    val loginCheck: LiveData<Boolean>
            get() = _loginCheck

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun onLoginButtonClick(){
        when {
            email.isNullOrEmpty() || password.isNullOrEmpty() -> {
                authListener?.onFailure("Invalid Email or Password $email $password")
                return
            }
            !isEmailValid(email!!) -> authListener?.onFailure("Invalid Email")
            else -> checkLogin()
        }
    }

    fun checkLogin(){
        coroutineScope.launch {
            val getPropertiesDeferred = LoginApi.retrofitService.checkLogin(email!!, password!!)
            try {
                val listResult = getPropertiesDeferred.await()
                if (listResult.status == 200){
                    _userData.value = listResult.data
                    _loginCheck.value = true
                    authListener?.onSuccess("${listResult.user_msg}")
                }else{
                    authListener?.onFailure("Login Unsuccessful\nError ${listResult.status} : ${listResult.user_msg}")
                    Timber.i("Error ${listResult.status} : ${listResult.message}")
                }
            }catch (t: Throwable){
                    authListener?.onFailure("Failure : ${t.message}")
                    Timber.i("Failure : ${t.message}")
            }
        }
    }

    /**
     *  Change login listener value
     */
    fun loginDone(){
        _loginCheck.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}