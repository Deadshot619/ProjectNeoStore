package com.deadshot.android.projectneostore.network

import com.deadshot.android.projectneostore.models.CommonResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface OrderNowApiService {
    @FormUrlEncoded
    @POST("order")
    fun orderNow(
        @Header("access_token") access_token: String,
        @Query("address") address: String
    ): Deferred<CommonResponse>
}

object OrderNowApi{
    val retrofitService: OrderNowApiService by lazy {
        retrofit_moshi.create(OrderNowApiService::class.java)
    }
}