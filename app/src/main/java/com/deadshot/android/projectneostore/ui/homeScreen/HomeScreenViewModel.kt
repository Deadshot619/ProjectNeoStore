package com.deadshot.android.projectneostore.ui.homeScreen

import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.User
import com.deadshot.android.projectneostore.ui.AuthListener
import io.paperdb.Paper
import timber.log.Timber

class HomeScreenViewModel : ViewModel(){

    var authListener: AuthListener? = null

    init {
//        val user: User = Paper.book().read("user_db")
//        Timber.i("Home Screen " + user.toString())
    }
}