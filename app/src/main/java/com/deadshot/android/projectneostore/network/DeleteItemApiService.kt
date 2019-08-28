package com.deadshot.android.projectneostore.network

import com.deadshot.android.projectneostore.models.DeleteItemResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface DeleteItemApiService{
    @FormUrlEncoded
    @POST("deleteCart")
    fun deleteItemFromCart(
        @Header("access_token") access_token: String,
        @Field("product_id") productId: String
    ): Deferred<DeleteItemResponse>
}

object DeleteItemApi{
    val retrofitService: DeleteItemApiService by lazy {
        retrofit_moshi.create(DeleteItemApiService::class.java)
    }
}