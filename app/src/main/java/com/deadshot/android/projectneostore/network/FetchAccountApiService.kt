package com.deadshot.android.projectneostore.network

import com.deadshot.android.projectneostore.models.UserDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface FetchAccountApiService {
    @GET("users/getUserData")
    fun getUserData(
        @Header("access_token") accessToken: String
    ): Call<UserDetails>
}

object FetchAccountApi{
    val retrofitService: FetchAccountApiService by lazy {
        retrofit.create(FetchAccountApiService::class.java)
    }
}