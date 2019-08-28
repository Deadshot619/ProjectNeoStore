package com.deadshot.android.projectneostore.ui

import androidx.fragment.app.Fragment
import com.deadshot.android.projectneostore.utils.toastShort


/**
 * This class implements Fragment() & Authlistener to be implemented by [BaseFragment] or any other class that just requires Authlistener
 */
abstract class BaseAuthListener : Fragment(), AuthListener{
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