package com.deadshot.android.projectneostore.network

import com.deadshot.android.projectneostore.models.ProductListResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * A public interface that exposes the [getProductList] method
 */
interface ProductListApiService{
    /**
     * Returns a Coroutine [Deferred] of [ProductListResponse] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "products/getList" endpoint will be requested with the GET
     * HTTP method
     */
//    @FormUrlEncoded
    @GET("products/getList")
    fun getProductList(
        @Query("product_category_id") productCategoryId: String,
        @Query("limit") limit: Number = 1,
        @Query("page") page: Number = 1
    ): Deferred<ProductListResponse>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object ProductListApi {
    val retrofitService : ProductListApiService by lazy {
        retrofit_moshi.create(ProductListApiService::class.java)
    }
}