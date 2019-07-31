package com.deadshot.android.projectneostore.utils

import android.text.Editable
import android.widget.Toast
import androidx.fragment.app.Fragment

val SHARED_PREFERENCE = "shared_preferences"
val FIRST_NAME= "first_name"
val LAST_NAME = "last_name"
val EMAIL = "email_id"
val PHONE_NUMBER = "phone_number"
val ACCESS_TOKEN = "access_token"
val DOB = "date_of_birth"
val PASSWORD = "password"

fun Fragment.toastShort(message: String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toastLong(message: String){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

//check if email is valid
fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)