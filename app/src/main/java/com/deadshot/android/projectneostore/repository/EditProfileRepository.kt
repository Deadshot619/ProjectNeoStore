package com.deadshot.android.projectneostore.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deadshot.android.projectneostore.network.UpdateAccountApi
import com.deadshot.android.projectneostore.ui.AuthListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class EditProfileRepository(private val access_token: String){

    var authListener = MutableLiveData<AuthListener?>()

    private val _checkUpdateSuccessful = MutableLiveData<Boolean>()
    val checkUpdateSuccessful: LiveData<Boolean>
        get() = _checkUpdateSuccessful


    /**
     * Update User Data
     */
    suspend fun updateUserData(first_name: String, last_name: String, email_id: String, phone_number: String, dob: String){
        withContext(Dispatchers.Main){
            val getPropertiesDeferred = UpdateAccountApi.retrofitService.updateAccount(
                access_token = access_token,
                first_name = first_name,
                last_name = last_name,
                email = email_id,
                phone_no = phone_number.toBigDecimal(),
                dob = dob,
                profile_pic = ""
            )
            try {
                /**
                 * - Calling await returns result from a network call when value is ready.
                 *  - await() : nonblocking, doesn't block UI
                 */
                val listResult = getPropertiesDeferred.await()
                if (listResult.status == 200) {
                    authListener.value?.onSuccess(listResult.user_msg)
                    _checkUpdateSuccessful.value =  true
                }else {
                    authListener.value?.onFailure("Error ${listResult.status} : ${listResult.user_msg}")
                }
            }catch (t: Throwable){
                Timber.i("Failure : ${t.message}")
            }
        }
    }

    fun updateDone(){
        _checkUpdateSuccessful.value = false
    }
}