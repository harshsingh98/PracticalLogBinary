package com.example.practicallogbinary.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface API {
    @POST("get-orders-v3.json")
    @FormUrlEncoded
    suspend fun getOrders(
        @HeaderMap header: Map<String, String>,
        @FieldMap fieldsMap: Map<String, Int>,
    ): Response<ResponseBody>
}