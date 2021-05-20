package com.example.triviaapp.data.remote

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://opentdb.com/api.php/"

    private val retrofitClient: Retrofit.Builder by lazy {

        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
    }

    val api: ApiInterface by lazy {
        retrofitClient
                .build()
                .create(ApiInterface::class.java)
    }
}