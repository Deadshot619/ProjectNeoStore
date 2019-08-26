package com.deadshot.android.projectneostore.network

import com.deadshot.android.projectneostore.models.CommonResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*

/**
 * A public interface that exposes the [orderNow] method
 */
interface OrderNowApiService {
    @FormUrlEncoded
    @POST("order")
    fun orderNow(
        @Header("access_token") access_token: String,
        @Field("address") address: String
    ): Deferred<CommonResponse>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object OrderNowApi{
    val retrofitService: OrderNowApiService by lazy {
        retrofit_moshi.create(OrderNowApiService::class.java)
    }
}