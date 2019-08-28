package com.deadshot.android.projectneostore.network

import com.deadshot.android.projectneostore.models.ProductDetailResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RateProductApiService {
    @FormUrlEncoded
    @POST("products/setRating")
    fun setProductRating(
        @Field("product_id") product_id: Int,
        @Field("rating") rating: Int?
    ): Deferred<ProductDetailResponse>
}

object RateProductApi{
    val retrofitService: RateProductApiService by lazy{
        retrofit_moshi.create(RateProductApiService::class.java)
    }
}