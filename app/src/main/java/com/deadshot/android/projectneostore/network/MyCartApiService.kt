package com.deadshot.android.projectneostore.network

import com.deadshot.android.projectneostore.models.MyCartResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header

interface MyCartApiService {
    @GET("cart")
    fun getMyCartDetails(
        @Header("access_token") access_token: String
    ): Deferred<MyCartResponse>
}

object MyCartApi{
    val retrofitService: MyCartApiService by lazy {
        retrofit_moshi.create(MyCartApiService::class.java)
    }
}