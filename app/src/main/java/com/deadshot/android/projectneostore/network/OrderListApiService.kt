package com.deadshot.android.projectneostore.network

import com.deadshot.android.projectneostore.models.OrderListResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * A public interface that exposes the [getOrderList] method
 */
interface OrderListApiService {
    @GET("orderList")
    fun getOrderList(
        @Header("access_token") access_token: String
    ): Deferred<OrderListResponse>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object OrderListApi{
    val retrofitService: OrderListApiService by lazy {
        retrofit_moshi.create(OrderListApiService::class.java)
    }
}
