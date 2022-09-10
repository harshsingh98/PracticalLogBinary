package com.example.practicallogbinary.models


import com.google.gson.annotations.SerializedName

data class CompanyDriver(
    @SerializedName("driver_eta")
    val driverEta: String?,
    @SerializedName("driver_id")
    val driverId: String?,
    @SerializedName("driver_image")
    val driverImage: String?,
    @SerializedName("driver_name")
    val driverName: String?,
    @SerializedName("driver_number")
    val driverNumber: String?,
    @SerializedName("driver_status")
    val driverStatus: Int?,
    @SerializedName("driver_thumb_image")
    val driverThumbImage: String?
)