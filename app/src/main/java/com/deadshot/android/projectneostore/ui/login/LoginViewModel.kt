package com.deadshot.android.projectneostore.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.User
import com.deadshot.android.projectneostore.network.AccessToken.access_token
import com.deadshot.android.projectneostore.network.LoginApi
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.isEmailValid
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class LoginViewModel : ViewModel(){

    var email: String? = null
    var password: String? = null
    var authListener: AuthListener? = null

    private val _loginCheck = MutableLiveData<Boolean>()
    val loginCheck: LiveData<Boolean>
            get() = _loginCheck

    fun onLoginButtonClick(){
        authListener?.onStarted()
        when {
            email.isNullOrEmpty() || password.isNullOrEmpty() -> {
                authListener?.onFailure("Invalid Email or Password $email $password")
                return
            }
            !isEmailValid(email!!) -> authListener?.onFailure("Invalid Email")
            else -> checkLogin()
        }
    }

    private fun checkLogin(){
        LoginApi.retrofitService.checkLogin(email!!, password!!).enqueue(object: Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                _loginCheck.value = false
                authListener?.onFailure("Failed : " + t.message!!)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                //login successful
                if(response.isSuccessful){
                    authListener?.onFailure(response.body()!!.message)
                    Timber.i(response.toString())
                    _loginCheck.value = true
                    access_token = response.body()!!.data.access_token
                    Timber.i(access_token)
                }else{
                    authListener?.onFailure("Login Unsuccessful")
                    Timber.i("Error ${response.code()} : ${response.message()}")
                }
            }
        })
    }

    /**
     *  Change login listener value
     */
    fun loginDone(){
        _loginCheck.value = false
    }
}