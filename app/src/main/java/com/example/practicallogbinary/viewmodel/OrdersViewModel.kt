package com.example.practicallogbinary.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicallogbinary.models.OrdersResponseModel
import com.example.practicallogbinary.repo.OrdersRepository

class OrdersViewModel : ViewModel() {
    private var getOrdersLiveData: MutableLiveData<OrdersResponseModel>? = null

    fun getOrdersList(
        restaurantId: Int,
        status: Int,
        page: Int
    ): MutableLiveData<OrdersResponseModel>? {
        getOrdersLiveData =
            OrdersRepository.getOrders(restaurantId, status, page)
        return getOrdersLiveData
    }
}