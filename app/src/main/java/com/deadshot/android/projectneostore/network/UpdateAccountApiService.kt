package com.deadshot.android.projectneostore.network

import com.deadshot.android.projectneostore.models.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface UpdateAccountApiService {
    @FormUrlEncoded
    @POST("users/update")
    fun updateAccount(
        @Header("access_token") access_token: String,
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("email") email: String,
        @Field("dob") dob: String,
        @Field("profile_pic") profile_pic: String,
        @Field("phone_no") phone_no: Number
    ): Call<User>
}

object UpdateAccountApi{
    val retrofitService: UpdateAccountApiService by lazy {
        retrofit.create(UpdateAccountApiService::class.java)
    }
}