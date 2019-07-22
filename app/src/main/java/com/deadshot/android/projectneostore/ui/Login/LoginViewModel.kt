package com.deadshot.android.projectneostore.ui.Login

import android.view.View
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.User
import com.deadshot.android.projectneostore.services.DestinationLoginService
import com.deadshot.android.projectneostore.services.ServiceBuilder
import com.deadshot.android.projectneostore.ui.AuthListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel(){

    var email: String? = null
    var password: String? = null
    var authListener: AuthListener? = null

    fun onLoginButtonClick(view: View){
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()){
            authListener?.onFailure("Invalid Email or Password")
            return
        }else{
            val destinationService = ServiceBuilder.buildService(DestinationLoginService::class.java)
            val requestCall = destinationService.checkLogin(email!!, password!!)

            requestCall.enqueue(object: Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    authListener?.onFailure("Login Failed")
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if(response.isSuccessful){
                        authListener?.onSuccess()
                    }else{
                        authListener?.onFailure("Login Unsuccessful")
                    }
                }
            })
        }
    }

}