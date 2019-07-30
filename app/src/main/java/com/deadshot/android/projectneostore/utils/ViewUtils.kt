package com.deadshot.android.projectneostore.utils

import android.text.Editable
import android.widget.Toast
import androidx.fragment.app.Fragment

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