package com.deadshot.android.projectneostore.network

import com.deadshot.android.projectneostore.models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface SignUpApiService{
    @FormUrlEncoded
    @POST("users/register")
    fun doSignUp(
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirm_password") confirm_password: String,
        @Field("gender") gender: String,
        @Field("phone_no") phone_no: Number
    ): Call<User>
}

object SignUpApi{
    val retrofitService: SignUpApiService by lazy {
        retrofit.create(SignUpApiService::class.java)
    }
}