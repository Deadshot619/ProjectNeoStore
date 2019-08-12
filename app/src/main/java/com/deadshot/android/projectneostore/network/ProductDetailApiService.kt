package com.deadshot.android.projectneostore.network

import com.deadshot.android.projectneostore.models.ProductDetailResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A public interface that exposes the [getProductDetail] method
 */
interface ProductDetailApiService {
    @GET("products/getDetail")
    fun getProductDetail(
        @Query("product_id") productId: Int
    ): Deferred<ProductDetailResponse>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object ProductDetailApi {
    val retrofitService : ProductDetailApiService by lazy {
        retrofit_moshi.create(ProductDetailApiService::class.java)
    }
}