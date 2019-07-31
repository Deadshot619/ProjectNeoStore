package com.deadshot.android.projectneostore.ui.myAccount

import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.ui.AuthListener

class MyAccountViewModel(
    firstName: String,
    lastName: String,
    email: String,
    phone: String,
    dob: String
) : ViewModel(){

    val first_name: String = firstName
    val last_name: String = lastName
    val email_id: String = email
    val phone_number: String = phone
    val dob: String = dob
    var authListener: AuthListener? = null

}