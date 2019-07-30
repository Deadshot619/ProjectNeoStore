package com.deadshot.android.projectneostore.ui.myAccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.UserDetails
import com.deadshot.android.projectneostore.network.AccessToken.access_token
import com.deadshot.android.projectneostore.network.FetchAccountApi
import com.deadshot.android.projectneostore.ui.AuthListener
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class MyAccountViewModel : ViewModel(){

    private val _firstName = MutableLiveData<String?>()
    val firstName: LiveData<String?>
        get() = _firstName


    private val _lastName = MutableLiveData<String?>()
    val lastName: LiveData<String?>
        get() = _lastName

    private val _emailId = MutableLiveData<String?>()
    val emailId: LiveData<String?>
        get() = _emailId

    private val _phoneNumber = MutableLiveData<String?>()
    val phoneNumber: LiveData<String?>
        get() = _phoneNumber

    private val _dob = MutableLiveData<String?>()
    val dob: LiveData<String?>
        get() = _dob

    var authListener: AuthListener? = null

    init {
        Timber.i("in Init Block")
        fetchDetails()
    }

    private fun fetchDetails(){
        FetchAccountApi.retrofitService.getUserData(accessToken = access_token!!)
            .enqueue(object : retrofit2.Callback<UserDetails>{
                override fun onFailure(call: Call<UserDetails>, t: Throwable) {
                    authListener?.onFailure(t.message.toString())
                }

                override fun onResponse(call: Call<UserDetails>, response: Response<UserDetails>) {
                    Timber.i(response.body()!!.data.toString())
                    if (response.isSuccessful){
                        Timber.i(response.body()!!.toString())
                        _firstName.value = response.body()!!.data.user_data.first_name
                        _lastName.value = response.body()!!.data.user_data.last_name
                        _emailId.value = response.body()!!.data.user_data.email
                        _phoneNumber.value = response.body()!!.data.user_data.phone_no.toString()
                    }else{
                        Timber.i("Details retrievel failed" + response.body())
                    }
                }

            })
    }
}