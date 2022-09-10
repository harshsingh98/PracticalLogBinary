package com.example.practicallogbinary.interfaces

import com.example.practicallogbinary.models.FoodOrder

interface OrderAdapterInterface {
    fun onItemClicked(foodOrder: FoodOrder)
}