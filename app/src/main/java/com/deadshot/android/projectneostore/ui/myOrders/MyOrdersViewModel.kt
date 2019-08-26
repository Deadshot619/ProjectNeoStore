package com.deadshot.android.projectneostore.ui.myOrders

import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.repository.OrderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyOrdersViewModel(accessToken: String) : ViewModel(){
    /**
     * Lazily Create an instance of [OrderRepository]
     */
    private val orderRepository by lazy {
        OrderRepository(access_token = accessToken)
    }

    val authListener = orderRepository.authListener
    val statusOrderList = orderRepository.statusOrderList

    val propertiesOrderList = orderRepository.propertiesOrderList

    private var viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        loadOrderList()
    }

    private fun loadOrderList() {
        coroutineScope.launch {
            orderRepository.orderList()
        }
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