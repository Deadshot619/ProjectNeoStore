package com.deadshot.android.projectneostore.network

import com.deadshot.android.projectneostore.models.User
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
    ): Deferred<User>
}

object SignUpApi{
    val retrofitService: SignUpApiService by lazy {
        retrofit.create(SignUpApiService::class.java)
    }
}