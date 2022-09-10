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


object DeleteFoodOrdersDBRepository {
    private var responseGetter = MutableLiveData<String>()

    fun deleteOrder(
        db: AppDatabase
    ): MutableLiveData<String> {
        responseGetter = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            callDeleteOrdersFromDB(db)
        }

        return responseGetter
    }

    private suspend fun callDeleteOrdersFromDB(db: AppDatabase) {
        try {
            val foodOrderDao: FoodOrderDao = db.foodOrderDao()

            if (foodOrderDao != null) {
                foodOrderDao.deleteAll()
                logD("CheckFoodOrderDeletedResponse", Gson().toJson(foodOrderDao.getAllOrders()))
                withContext(Dispatchers.Main) {
                    responseGetter.value = "Food order deleted"
                }
            } else {
                withContext(Dispatchers.Main) {
                    responseGetter.value = "Error in deleting food order"
                }
            }
        } catch (e: Exception) {
            logE("CheckFoodOrderAddedException", e.localizedMessage)
            withContext(Dispatchers.Main) {
                responseGetter.value = "Exception in deleting food order"
            }
        }
    }
}