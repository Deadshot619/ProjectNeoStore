package com.deadshot.android.projectneostore.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.User
import com.deadshot.android.projectneostore.network.LoginApi
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.isEmailValid
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                if(response.isSuccessful){
                    authListener?.onSuccess()
                    authListener?.onFailure(response.body()?.data!!.username)

                    _loginCheck.value = true
                }else{
                    authListener?.onFailure("Login Unsuccessful")
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