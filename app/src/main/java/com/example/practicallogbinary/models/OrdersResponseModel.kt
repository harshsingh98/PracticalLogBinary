package com.example.practicallogbinary.models

data class OrdersResponseModel (
    val success: Boolean,
    val message: String,
    var listOfFoodOrders: ArrayList<FoodOrder> = ArrayList()
)