package com.deadshot.android.projectneostore.ui

import android.content.Context
import androidx.fragment.app.Fragment
import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.utils.ACCESS_TOKEN
import com.deadshot.android.projectneostore.utils.SHARED_PREFERENCE
import com.deadshot.android.projectneostore.utils.toastShort

open class BaseFragment : Fragment(), AuthListener{

    protected lateinit var accessToken: String

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