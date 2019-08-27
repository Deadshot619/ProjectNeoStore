package com.deadshot.android.projectneostore.ui

import android.content.Context
import androidx.fragment.app.Fragment
import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.utils.*

abstract class BaseFragment : Fragment(), AuthListener{


    protected lateinit var firstName: String
    protected lateinit var lastName: String
    protected lateinit var accessToken: String
    protected var email: String? = null
    private var password: String? = null


    /**
     *  Save data in Shared Preferences
     */
    protected fun saveAuthData(firstName: String, lastName: String, email: String, phone: String, accessToken: String, dob: String){
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE) ?: return
        with(sharedPreferences.edit()){
            putString(FIRST_NAME, firstName)
            putString(LAST_NAME, lastName)
            putString(EMAIL, email)
            putString(PHONE_NUMBER, phone)
            putString(ACCESS_TOKEN, accessToken)
            putString(DOB, dob)
            apply()
        }
    }

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


    protected fun loadUserData(){
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE) ?: return
        firstName = sharedPreferences.getString(FIRST_NAME, getString(R.string.default_value))!!
        lastName = sharedPreferences.getString(LAST_NAME, getString(R.string.default_value))!!
        email = sharedPreferences.getString(EMAIL, getString(R.string.default_value))!!
    }


    /**
     * Load data from shared preferences
     */
    protected fun loadAuthData() {
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE) ?: return
        email = sharedPreferences.getString(EMAIL,null)
        password = sharedPreferences.getString(PASSWORD, null)
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