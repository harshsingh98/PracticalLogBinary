package com.example.practicallogbinary.models


import com.google.gson.annotations.SerializedName

data class OrdersData(
    @SerializedName("orderInfo")
    val orderInfo: OrderInfo?,
    @SerializedName("total_record")
    val totalRecord: Int?
)