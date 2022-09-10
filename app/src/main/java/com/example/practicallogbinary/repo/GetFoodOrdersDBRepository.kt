package com.example.practicallogbinary.repo

import androidx.lifecycle.MutableLiveData
import com.example.practicallogbinary.api.RetrofitHelper
import com.example.practicallogbinary.consts.AppConsts
import com.example.practicallogbinary.database.AppDatabase
import com.example.practicallogbinary.database.FoodOrderDao
import com.example.practicallogbinary.models.FoodOrder
import com.example.practicallogbinary.models.OrdersResponseModel
import com.example.practicallogbinary.utils.ExtFuncs.logD
import com.example.practicallogbinary.utils.ExtFuncs.logE
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object GetFoodOrdersDBRepository {
    private var responseGetter = MutableLiveData<OrdersResponseModel>()

    fun getOrders(
        db: AppDatabase
    ): MutableLiveData<OrdersResponseModel> {
        responseGetter = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            callGetOrdersFromDB(db)
        }

        return responseGetter
    }

    private suspend fun callGetOrdersFromDB(db: AppDatabase) {
        try {
            val foodOrderDao: FoodOrderDao = db.foodOrderDao()
            val listOfOrders: ArrayList<FoodOrder> = foodOrderDao.getAllOrders() as ArrayList<FoodOrder>

            logD("CheckFoodOrdersResponse", Gson().toJson(listOfOrders))
            withContext(Dispatchers.Main) {
                responseGetter.value = OrdersResponseModel(
                    success = true,
                    message = "Orders fetched",
                    listOfFoodOrders = listOfOrders
                )
            }
        } catch (e: Exception) {
            logE("CheckFoodOrdersException", e.localizedMessage)
            withContext(Dispatchers.Main) {
                responseGetter.value = OrdersResponseModel(
                    success = false,
                    message = "Exception in fetching orders"
                )
            }
        }
    }
}