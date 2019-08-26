package com.deadshot.android.projectneostore.network

import com.deadshot.android.projectneostore.models.OrderDetailResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * A public interface that exposes the [getOrderDetail] method
 */
interface OrderDetailApiService {
    @GET("orderDetail")
    fun getOrderDetail(
        @Header("access_token") access_token: String,
        @Query("order_id") orderId: Int
    ): Deferred<OrderDetailResponse>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object OrderDetailApi{
    val retrofitService: OrderDetailApiService by lazy {
        retrofit_moshi.create(OrderDetailApiService::class.java)
    }
}