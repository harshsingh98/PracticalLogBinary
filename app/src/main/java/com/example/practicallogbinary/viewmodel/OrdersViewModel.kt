package com.example.practicallogbinary.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicallogbinary.database.AppDatabase
import com.example.practicallogbinary.models.FoodOrder
import com.example.practicallogbinary.models.OrdersResponseModel
import com.example.practicallogbinary.repo.DeleteFoodOrdersDBRepository
import com.example.practicallogbinary.repo.GetFoodOrdersDBRepository
import com.example.practicallogbinary.repo.InsertFoodOrdersDBRepository
import com.example.practicallogbinary.repo.OrdersRepository

class OrdersViewModel : ViewModel() {
    //get orders from api
    private var getOrdersLiveData: MutableLiveData<OrdersResponseModel>? = null

    //get orders from database
    private var getOrdersFromDBLiveData: MutableLiveData<OrdersResponseModel>? = null

    //add order in database
    private var addOrderInDBLiveData: MutableLiveData<String>? = null

    //delete orders from api
    private var deleteOrdersLiveData: MutableLiveData<String>? = null

    fun getOrdersList(
        restaurantId: Int,
        status: Int,
        page: Int
    ): MutableLiveData<OrdersResponseModel>? {
        getOrdersLiveData =
            OrdersRepository.getOrders(restaurantId, status, page)
        return getOrdersLiveData
    }

    fun getOrdersListFromDB(
        db: AppDatabase
    ): MutableLiveData<OrdersResponseModel>? {
        getOrdersFromDBLiveData =
            GetFoodOrdersDBRepository.getOrders(db)
        return getOrdersFromDBLiveData
    }

    fun addOrderInDB(
        db: AppDatabase,
        foodOrder: FoodOrder
    ): MutableLiveData<String>? {
        addOrderInDBLiveData =
            InsertFoodOrdersDBRepository.insertOrder(db, foodOrder)
        return addOrderInDBLiveData
    }

    fun deleteOrdersListFromDB(
        db: AppDatabase
    ): MutableLiveData<String>? {
        deleteOrdersLiveData =
            DeleteFoodOrdersDBRepository.deleteOrder(db)
        return deleteOrdersLiveData
    }
}