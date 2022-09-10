package com.example.practicallogbinary.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.practicallogbinary.models.FoodOrder

@Dao
interface FoodOrderDao {

    @Query("SELECT * FROM FoodOrder")
    suspend fun getAllOrders(): List<FoodOrder>

    @Insert
    fun insert(users: FoodOrder)

    @Query("DELETE FROM FoodOrder")
    fun deleteAll()
}