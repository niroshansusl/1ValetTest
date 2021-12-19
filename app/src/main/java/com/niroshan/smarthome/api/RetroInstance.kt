package com.niroshan.smarthome.api

import com.niroshan.smarthome.api.response.DeviceCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object {
        private const val BASE_URL = "https://mock.com/"

        private val mockInterceptor = MockInterceptor()
        private val mockClient = OkHttpClient.Builder()
            .addInterceptor(mockInterceptor)
            .build()

        fun getRetroInstance(): Retrofit {

            return Retrofit.Builder().baseUrl(BASE_URL)
                .client(mockClient)
                .addCallAdapterFactory(DeviceCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
    }
}