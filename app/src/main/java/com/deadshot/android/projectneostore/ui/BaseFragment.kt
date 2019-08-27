package com.deadshot.android.projectneostore.ui

import android.content.Context
import androidx.fragment.app.Fragment
import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.utils.*

open class BaseFragment : Fragment(), AuthListener{

    protected lateinit var accessToken: String


    /**
     *  Save data in Shared Preferences
     */
    protected fun saveUserData(firstName: String, lastName: String, email: String, phone: String, dob: String){
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE) ?: return
        with(sharedPreferences.edit()){
            putString(FIRST_NAME , firstName)
            putString(LAST_NAME , lastName)
            putString(EMAIL , email)
            putString(PHONE_NUMBER , phone)
            putString(DOB , dob)
            apply()
        }
    }

    /**
     * Load data from shared preferences
     */
    protected fun loadAccessToken(){
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCE , Context.MODE_PRIVATE) ?: return
        accessToken = sharedPreferences.getString(ACCESS_TOKEN , getString(R.string.default_value))!!
    }

    override fun onStarted() {
        toastShort("Login Started")
    }

    override fun onSuccess(message: String) {
        toastShort(message)
    }

    override fun onFailure(message: String) {
        toastShort(message)
    }
}