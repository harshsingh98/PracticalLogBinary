package com.example.practicallogbinary.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class FoodOrder(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    @ColumnInfo(name = "expectedDate")
    @SerializedName("expected_date")
    val expectedDate: String?,
    @ColumnInfo(name = "orderId")
    @SerializedName("order_id")
    val orderId: Int?,
    @ColumnInfo(name = "orderType")
    @SerializedName("order_type")
    val orderType: String?,
    @ColumnInfo(name = "sequenceNo")
    @SerializedName("sequence_no")
    val sequenceNo: Int?,
)