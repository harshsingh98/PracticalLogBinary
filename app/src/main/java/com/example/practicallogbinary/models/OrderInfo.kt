package com.example.practicallogbinary.models


import com.google.gson.annotations.SerializedName

data class OrderInfo(
    @SerializedName("orders")
    val foodOrders: ArrayList<FoodOrder>?
)