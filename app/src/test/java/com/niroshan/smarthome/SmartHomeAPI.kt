package com.niroshan.smarthome

import com.niroshan.smarthome.data.DeviceList
import retrofit2.http.GET

interface SmartHomeAPI {
    @GET("deviceList")
    suspend fun getDeviceDataFromAPI(): List<DeviceList>
}