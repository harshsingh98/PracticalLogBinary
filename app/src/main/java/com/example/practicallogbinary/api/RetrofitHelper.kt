package com.example.practicallogbinary.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitHelper {
    fun makeRetrofitService(): API {
        var base_url: String = "https://qaadmin.onzway.com/apis/"

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val bodyInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient().newBuilder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(bodyInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(base_url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(API::class.java)
    } }