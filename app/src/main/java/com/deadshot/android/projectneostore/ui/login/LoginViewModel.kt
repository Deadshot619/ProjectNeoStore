package com.deadshot.android.projectneostore.ui.login

import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.repository.LoginRepository
import com.deadshot.android.projectneostore.utils.isEmailValid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(){

    var email: String? = null
    var password: String? = null

    private val loginRepository by lazy {
        LoginRepository()
    }

    var authListener = loginRepository.authListener

    val userData = loginRepository.userData

    val loginCheck = loginRepository.loginCheck

    val progressBarStatus = loginRepository.progressBarStatus

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun onLoginButtonClick(){
        loginRepository.showProgressBar()
        when {
            email.isNullOrEmpty() || password.isNullOrEmpty() -> {
                authListener.value?.onFailure("Invalid Email or Password")
                loginRepository.hideProgressBar()
                return
            }
            !isEmailValid(email!!) -> {
                authListener.value?.onFailure("Invalid Email")
                loginRepository.hideProgressBar()
            }
            else -> coroutineScope.launch {  loginRepository.checkLogin(email = email, password = password) }
        }
    }

    /**
     *  Change login listener value
     */
    fun loginDone(){
        loginRepository.loginDone()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}