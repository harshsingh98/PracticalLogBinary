package com.example.practicallogbinary.models


import com.google.gson.annotations.SerializedName

data class PrintItemName(
    @SerializedName("item_name")
    val itemName: String?,
    @SerializedName("printer")
    val printer: String?
)