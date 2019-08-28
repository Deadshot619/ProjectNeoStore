package com.deadshot.android.projectneostore.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deadshot.android.projectneostore.models.OrderDetail
import com.deadshot.android.projectneostore.models.OrderList
import com.deadshot.android.projectneostore.models.ProductsInfo
import com.deadshot.android.projectneostore.network.OrderDetailApi
import com.deadshot.android.projectneostore.network.OrderListApi
import com.deadshot.android.projectneostore.network.OrderNowApi
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.EnumCart
import com.deadshot.android.projectneostore.utils.LoadingProductsStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class OrderRepository (private val access_token: String){
    val authListener = MutableLiveData<AuthListener>()

    private val _statusOrderList = MutableLiveData<LoadingProductsStatus>()
    val statusOrderList: LiveData<LoadingProductsStatus>
        get() = _statusOrderList

    /**
     * Status to check whether cart is empty
     */
    private val _hasOrderStatus = MutableLiveData<EnumCart>()
    val hasOrderStatus: LiveData<EnumCart>
        get() = _hasOrderStatus

    private val _propertiesOrderList = MutableLiveData<List<OrderList>>()
    val propertiesOrderList: LiveData<List<OrderList>>
        get() = _propertiesOrderList

    private val _propertiesOrderDetail = MutableLiveData<OrderDetail>()
    val propertiesOrderDetail: LiveData<OrderDetail>
    get() = _propertiesOrderDetail

    suspend fun placeOrder(access_token: String, address: String){
        withContext(Dispatchers.Main){
            val getPropertiesDeferred = OrderNowApi.retrofitService
                .orderNow(access_token = access_token, address = address)

            try {
                val listResult = getPropertiesDeferred.await()
                if (listResult.status == 200){
                    authListener.value?.onSuccess("${listResult.user_msg}")
//                    _status.value = true
                }else{
                    authListener.value?.onFailure("Error ${listResult.status} : ${listResult.user_msg}")
//                    _status.value = false
                }
            }catch (t: Throwable){
                authListener.value?.onFailure("Failure : ${t.message}")
                Timber.i("Failure : ${t.message}")
//                _status.value = false
            }
        }
    }

    suspend fun orderList(){
        withContext(Dispatchers.Main){
            val getPropertiesDeferred = OrderListApi.retrofitService.getOrderList(access_token = access_token)

            try {
                _statusOrderList.value = LoadingProductsStatus.LOADING
                val listResult = getPropertiesDeferred.await()
                if (listResult.status == 200){
                    if (listResult.orderList.isNullOrEmpty()){
                        _hasOrderStatus.value = EnumCart.CARTEMPTY
                    }else{
                        _propertiesOrderList.value = listResult.orderList
//                        authListener.value?.onSuccess("${listResult.user_msg}")
                        _hasOrderStatus.value = EnumCart.CARTNOTEMPTY
                    }
                    _statusOrderList.value = LoadingProductsStatus.DONE
                }else{
                    _propertiesOrderList.value = null
                    authListener.value?.onFailure("Error ${listResult.status} : ${listResult.user_msg}")
                    _statusOrderList.value = LoadingProductsStatus.ERROR

                }
            }catch (t: Throwable){
                _propertiesOrderList.value = null
                authListener.value?.onFailure("Failure : ${t.message}")
                Timber.i("Failure : ${t.message}")
                _statusOrderList.value = LoadingProductsStatus.ERROR
            }
        }
    }

    suspend fun getOrderDetail(orderId: Int){
        withContext(Dispatchers.Main){
            val getPropertiesDeferred = OrderDetailApi.retrofitService
                .getOrderDetail(access_token = access_token, orderId = orderId)

            try {
                _statusOrderList.value = LoadingProductsStatus.LOADING
                val listResult = getPropertiesDeferred.await()
                if (listResult.status == 200){
                    _propertiesOrderDetail.value = listResult.orderDetail
//                    authListener.value?.onSuccess("${listResult.user_msg}")
                    _statusOrderList.value = LoadingProductsStatus.DONE
                    Timber.i("${listResult.toString()}")
                }else{
                    _propertiesOrderDetail.value = null
                    authListener.value?.onFailure("Error ${listResult.status} : ${listResult.user_msg}")
                    _statusOrderList.value = LoadingProductsStatus.ERROR
                    Timber.i("${listResult.toString()}")
                }
            }catch (t: Throwable){
                _propertiesOrderDetail.value = null
                authListener.value?.onFailure("Failure : ${t.message}")
                Timber.i("Failure : ${t.message}")
                _statusOrderList.value = LoadingProductsStatus.ERROR
            }
        }
    }
}