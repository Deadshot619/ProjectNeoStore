package com.deadshot.android.projectneostore.network

import com.deadshot.android.projectneostore.models.CartResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface EditCartApiService {
    @FormUrlEncoded
    @POST("editCart")
    fun addToCart(
        @Header("access_token") access_token: String ,
        @Field("product_id") productId: String ,
        @Field("quantity") quantity: String
    ) : Deferred<CartResponse>
}

object EditCartApi{
    val retrofitService: EditCartApiService by lazy {
        retrofit_moshi.create(EditCartApiService::class.java)
    }
}