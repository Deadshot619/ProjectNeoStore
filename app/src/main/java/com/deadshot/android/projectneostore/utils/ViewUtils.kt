package com.deadshot.android.projectneostore.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.regex.Pattern

const val SHARED_PREFERENCE = "shared_preferences"
const val FIRST_NAME= "first_name"
const val LAST_NAME = "last_name"
const val EMAIL = "email_id"
const val PHONE_NUMBER = "phone_number"
const val ACCESS_TOKEN = "access_token"
const val DOB = "date_of_birth"
const val PASSWORD = "password"
const val TABLES = 1
const val SOFAS = 2
const val CHAIRS = 3
const val CUPBOARDS = 4

enum class LoadingProductsStatus{ LOADING, ERROR, DONE }

fun Fragment.toastShort(message: String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toastLong(message: String){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

/**
 * Check if Name is valid
 */
fun isNameValid(name: String): Boolean{
    return Pattern.matches("[a-zA-Z]+", name)
}

/**
 * check if email is valid
 */
fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

/**
 * Check if password is valid
 */
fun isPasswordValid(password: String?, confirmPassword: String?): Boolean {
    return password.equals(confirmPassword)
}

/**
 * Check if password contains 0-9, a-z, A-Z
 */
fun isPasswordContainCharacter(password: String?): Boolean{
    return Pattern.matches("[a-zA-Z0-9]+", password)
}

/**
 * Check if Mobile Number is valid
 */
fun isValidMobile(phone: String): Boolean{
    return Pattern.matches("[0-9]+", phone) && phone.length in 7..13
}