package com.deadshot.android.projectneostore.ui

interface AuthListener {
    fun onStarted()
    fun onSuccess(message: String)
    fun onFailure(message: String)
}