package com.deadshot.android.projectneostore.ui

import android.content.Context
import androidx.fragment.app.Fragment
import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.utils.*

abstract class BaseFragment : BaseAuthListener(){

    protected lateinit var firstName: String
    protected lateinit var lastName: String
    protected lateinit var accessToken: String
    protected lateinit var email: String

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


    protected open fun loadUserData(){
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE) ?: return
        firstName = sharedPreferences.getString(FIRST_NAME, getString(R.string.default_value))!!
        lastName = sharedPreferences.getString(LAST_NAME, getString(R.string.default_value))!!
        email = sharedPreferences.getString(EMAIL, getString(R.string.default_value))!!
    }
}