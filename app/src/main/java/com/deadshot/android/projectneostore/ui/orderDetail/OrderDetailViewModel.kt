package com.deadshot.android.projectneostore.ui.orderDetail

import androidx.lifecycle.ViewModel
import com.deadshot.android.projectneostore.repository.OrderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OrderDetailViewModel(accessToken: String , private val orderId: Int) : ViewModel(){
    /**
     * Lazily Create an instance of [OrderRepository]
     */
    private val orderRepository by lazy {
        OrderRepository(access_token = accessToken)
    }

    val authListener = orderRepository.authListener
    val statusOrderList = orderRepository.statusOrderList

    val propertiesOrderDetail= orderRepository.propertiesOrderDetail

    val viewModelJob = Job()
    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        loadOrderDetail()
    }

    private fun loadOrderDetail() {
        coroutineScope.launch {
            orderRepository.getOrderDetail(orderId = orderId)
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