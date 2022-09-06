package com.example.practicallogbinary.models

data class OrdersResponseModel (
    val success: Boolean,
    val message: String,
    var listOfOrders: ArrayList<String> = ArrayList()
)