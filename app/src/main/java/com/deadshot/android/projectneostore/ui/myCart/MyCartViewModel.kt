package com.deadshot.android.projectneostore.ui.myCart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.repository.MyCartRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyCartViewModel(private val accessToken: String) : ViewModel(){
    /**
     * Lazily Create an instance of [MyCartRepository]
     */
    private val myCartRepository by lazy {
        MyCartRepository(accessToken)
    }

    val authListener = myCartRepository.authListener

    val status = myCartRepository.status

    val properties =  myCartRepository.propertiesProductInfo

    val propertiesMyCartResponse = myCartRepository.propertiesMyCartResponse

    val reloadCartStatus = myCartRepository.reloadCartStatus

    private val _navigateToAddressStatus = MutableLiveData<Boolean>()
    val navigateToAddressStatus: LiveData<Boolean>
    get() = _navigateToAddressStatus


    /**
     * to check whether cart is empty or not
     */
    val cartStatus = myCartRepository.cartStatus

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        loadCart()
    }

    fun deleteFromCart(productId: Int){
        coroutineScope.launch {
            myCartRepository.deleteFromCart(productId)
        }
    }

    /**
     * Reloads My Cart
     */
    fun loadCart(){
        coroutineScope.launch {
            myCartRepository.getCartDetails()
        }
    }

    /**
     * Edit cart
     */
    fun editCart(productId: Int, quantity: Int){
        coroutineScope.launch {
            myCartRepository.editCart(productId, quantity)
        }
    }

    fun orderNow(){
        navigateToAddress()
//        coroutineScope.launch {
//            myCartRepository.placeOrder(access_token = accessToken, address = "201, Sukhjit Apt.")
//        }
    }

    fun deleteItemDone(){
        myCartRepository.deleteItemDone()
    }

    fun deleteItemfailed(){
        myCartRepository.deleteItemfailed()
    }

    fun navigateToAddress(){
        _navigateToAddressStatus.value = true
    }

    fun navigateToAddressDone(){
        _navigateToAddressStatus.value = false
    }

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}