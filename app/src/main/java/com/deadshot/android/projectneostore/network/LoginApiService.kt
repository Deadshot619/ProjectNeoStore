package com.deadshot.android.projectneostore.network

import com.deadshot.android.projectneostore.models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

private const val BASE_URL = "http://staging.php-dev.in:8844/trainingapp/api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface LoginApiService{
    @FormUrlEncoded
    @POST("users/login")
    fun checkLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<User>
}

object LoginApi{
    val retrofitService: LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }
}