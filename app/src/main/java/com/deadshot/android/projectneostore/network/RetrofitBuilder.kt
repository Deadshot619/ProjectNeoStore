package com.deadshot.android.projectneostore.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()