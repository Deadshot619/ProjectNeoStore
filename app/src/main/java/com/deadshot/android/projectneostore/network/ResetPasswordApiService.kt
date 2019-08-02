package com.deadshot.android.projectneostore.network

import com.deadshot.android.projectneostore.models.User
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ResetPasswordApiService {
    @FormUrlEncoded
    @POST("users/change")
    fun makePasswordReset(
        @Header("access_token") access_token: String,
        @Field("old_password") old_password: String,
        @Field("password") new_password: String,
        @Field("confirm_password") confirm_password: String
    ): Deferred<User>
}

object ResetPasswordApi{
    val retrofitService: ResetPasswordApiService by lazy {
        retrofit.create(ResetPasswordApiService::class.java)
    }
}