package com.deadshot.android.projectneostore.ui.homeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.models.User
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.EnumProductList
import io.paperdb.Paper
import timber.log.Timber

class HomeScreenViewModel : ViewModel(){

    //Live data for Navigation to products
    private val _navigateToProduct = MutableLiveData<EnumProductList>()
    val navigateToProduct: LiveData<EnumProductList>
        get() = _navigateToProduct

    //called on click of Tables Button
    fun onClickNavigateToTables(){
        _navigateToProduct.value = EnumProductList.TABLES
    }

    //called on click of Sofas Button
    fun onClickNavigateToSofas(){
        _navigateToProduct.value = EnumProductList.SOFAS
    }

    //called on click of Cupboards Button
    fun onClickNavigateToCupboards(){
        _navigateToProduct.value = EnumProductList.CUPBOARDS
    }

    //called on click of Chairs Button
    fun onClickNavigateToChairs(){
        _navigateToProduct.value = EnumProductList.CHAIRS
    }

    fun navigateToProductDone(){
        _navigateToProduct.value = null
    }
}