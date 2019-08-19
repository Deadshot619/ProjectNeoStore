package com.deadshot.android.projectneostore.ui.resetPassword

import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.repository.ResetPasswordRepository
import com.deadshot.android.projectneostore.utils.isPasswordContainCharacter
import com.deadshot.android.projectneostore.utils.isPasswordValid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ResetPasswordViewModel(private val accessToken: String) : ViewModel(){
    var oldPassword = ""
    var newPassword = ""
    var confirmPassword = ""

    private val resetPasswordRepository by lazy {
        ResetPasswordRepository(accessToken = accessToken)
    }

    var authListener= resetPasswordRepository.authListener

    val checkResetPasswordSuccessful = resetPasswordRepository.checkResetPasswordSuccessful

    /**
     * Set Job & coroutine scope
     */
    private var viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun onClickSave(){
        if (checkFieldsFilled() && checkFieldsCorrect() && checkCharacters()){
            coroutineScope.launch {
                resetPasswordRepository.resetPassword(oldPassword, newPassword, confirmPassword)
            }
        }
    }

    fun resetDone(){
        resetPasswordRepository.resetDone()
    }

    /**
     * Check if fields contains correct characters
     */
    private fun checkCharacters(): Boolean{
        return when{
            !isPasswordContainCharacter(oldPassword) || !isPasswordContainCharacter(newPassword) || !isPasswordContainCharacter(confirmPassword) -> {
                authListener.value?.onFailure("Passwords should contain only Alphanumeric Characters")
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
                authListener.value?.onFailure("Password Fields cannot be empty")
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
                authListener.value?.onFailure("Old Password & New Password cannot be same!")
                false
            }
            !isPasswordValid(newPassword, confirmPassword) -> {
                authListener.value?.onFailure("Passwords do not match!")
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