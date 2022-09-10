package com.example.practicallogbinary.models


import com.google.gson.annotations.SerializedName

data class OrdersListResponse(
    @SerializedName("data")
    val ordersData: OrdersData?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
)