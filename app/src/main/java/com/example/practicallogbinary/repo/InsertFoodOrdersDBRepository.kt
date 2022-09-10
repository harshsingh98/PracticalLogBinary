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


object InsertFoodOrdersDBRepository {
    private var responseGetter = MutableLiveData<String>()

    fun insertOrder(
        db: AppDatabase,
        foodOrder: FoodOrder
    ): MutableLiveData<String> {
        responseGetter = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            callInsertOrderInDB(db, foodOrder)
        }

        return responseGetter
    }

    private suspend fun callInsertOrderInDB(db: AppDatabase, foodOrder: FoodOrder) {
        try {
            val foodOrderDao: FoodOrderDao = db.foodOrderDao()

            if (foodOrderDao != null) {
                foodOrderDao.insert(foodOrder)
                logD("CheckFoodOrderAddedResponse", Gson().toJson(foodOrderDao.getAllOrders()))
                withContext(Dispatchers.Main) {
                    responseGetter.value = "Food order Added"
                }
            } else {
                withContext(Dispatchers.Main) {
                    responseGetter.value = "Error in adding food order"
                }
            }
        } catch (e: Exception) {
            logE("CheckFoodOrderAddedException", e.localizedMessage)
            withContext(Dispatchers.Main) {
                responseGetter.value = "Exception in adding food order"
            }
        }
    }
}