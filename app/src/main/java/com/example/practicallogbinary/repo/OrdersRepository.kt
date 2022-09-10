package com.example.practicallogbinary.repo

import androidx.lifecycle.MutableLiveData
import com.example.practicallogbinary.api.RetrofitHelper
import com.example.practicallogbinary.consts.AppConsts
import com.example.practicallogbinary.models.OrdersResponseModel
import com.example.practicallogbinary.utils.ExtFuncs.logD
import com.example.practicallogbinary.utils.ExtFuncs.logE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object OrdersRepository {
    private var responseGetter = MutableLiveData<OrdersResponseModel>()

    fun getOrders(
        restaurantId: Int,
        status: Int,
        page: Int
    ): MutableLiveData<OrdersResponseModel> {
        responseGetter = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            callGetOrdersApi(restaurantId, status, page)
        }

        return responseGetter
    }

    private suspend fun callGetOrdersApi(restaurantId: Int,
                                         status: Int,
                                         page: Int) {
        try {
            val retrofit =
                RetrofitHelper().makeRetrofitService()

            val header = HashMap<String, String>()
            //header["Content-Type"] = "application/json"
            header["app_token"] = "JWT eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJmaXJlYmFzZS1hZG1pbnNkay04bHRpbkBvbnp3YXktZHJpdmVyLTNkZjU4LmlhbS5nc2VydmljZWFjY291bnQuY29tIiwic3ViIjoiZmlyZWJhc2UtYWRtaW5zZGstOGx0aW5Ab256d2F5LWRyaXZlci0zZGY1OC5pYW0uZ3NlcnZpY2VhY2NvdW50LmNvbSIsImF1ZCI6Imh0dHBzOlwvXC9pZGVudGl0eXRvb2xraXQuZ29vZ2xlYXBpcy5jb21cL2dvb2dsZS5pZGVudGl0eS5pZGVudGl0eXRvb2xraXQudjEuSWRlbnRpdHlUb29sa2l0IiwiaWF0IjoxNjYyNDc1MzQ2LCJleHAiOjE2NjI0Nzg5NDYsInVpZCI6ODgsImNsYWltcyI6eyJwcmVtaXVtX2FjY291bnQiOmZhbHNlfX0.YrNlsAmr_UGiomvRu-yZF8lxRJ8ADAXkONwJduA7-PWnOg6iETXNwBm9NdK4yK2Uw-b-6--GYU0x9sE8wNswAXZLy7vloSaZZ92L51dDswYJmTitSAE9Ua-Dnuub-sbwOknHvUszqWlNNOjS_j2lRitSJcV8zjIqz0CsZ9PHVCf_hSnp0hnj1Q06bRG0Z7AfY4DHyy3O0gDdbx0gDH9-PCJarwhtN8AqJbR-LZbsHYfwe1U13OWFBtM6EGGTuO4CO1ywbJ2Yr2QiphvPWGR4aIoizXhIers9z4BLM8j1EBEqRV6cGVoO3_Vyo2UKCnogc9LJ-C6iwX_1ZE2tk_pyEw"

            val params: HashMap<String, Int> = HashMap()
            params["restaurant_id"] = 1
            params["status"] = 1
            params["page"] = 1

            val apiResponse = withContext(Dispatchers.IO) {
                retrofit.getOrders(
                    header,
                    params
                )
            }

            if (apiResponse.isSuccessful) {

                logD("CheckFoodOrdersApiResponse", apiResponse.body().toString())
                if (apiResponse.code() == AppConsts.STATUS_CODE_OK) {
                    try {
                        withContext(Dispatchers.Main) {
                            responseGetter.value = OrdersResponseModel(
                                success = true,
                                message = apiResponse.message(),
                                listOfFoodOrders = apiResponse.body()?.ordersData?.orderInfo?.foodOrders!!
                            )
                        }
                    } catch (e: Exception) {
                        logE("CheckFoodOrdersApiException", e.localizedMessage)
                        withContext(Dispatchers.Main) {
                            responseGetter.value = OrdersResponseModel(
                                success = false,
                                message = "Some message"
                            )
                        }
                    }
                }
                else{
                    logD("CheckFoodOrdersApiErrorMain", apiResponse.errorBody()?.string() ?: "Error")
                    withContext(Dispatchers.Main) {
                        responseGetter.value = OrdersResponseModel(
                            success = false,
                            message = "Some message"
                        )
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    responseGetter.value = OrdersResponseModel(
                        success = false,
                        message = "Some message"
                    )
                }
            }
        } catch (e: Exception) {
            logE("CheckAcceptInvitationResponseOutsideException", e.localizedMessage)
            withContext(Dispatchers.Main) {
                responseGetter.value = OrdersResponseModel(
                    success = false,
                    message = "Some message"
                )
            }
        }
    }
}