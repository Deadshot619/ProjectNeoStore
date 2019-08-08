package com.deadshot.android.projectneostore.ui.homeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.User
import com.deadshot.android.projectneostore.ui.AuthListener
import io.paperdb.Paper
import timber.log.Timber

class HomeScreenViewModel : ViewModel(){

    //Live data for Navigation to tables
    private val _navigateToTable = MutableLiveData<Boolean>()
    val navigateToTable: LiveData<Boolean>
        get() = _navigateToTable


    //called on click of Tables Button
    fun onClickNavigateToTables(){
        _navigateToTable.value = true
    }

    fun navigateToTablesDone(){
        _navigateToTable.value = null
    }
}