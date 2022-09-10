package com.example.practicallogbinary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.practicallogbinary.models.FoodOrder

@Database(entities = [FoodOrder::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodOrderDao(): FoodOrderDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "food_orders_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}