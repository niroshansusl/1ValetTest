package com.niroshan.smarthome.api.response

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ResultAdapter(private val type: Type): CallAdapter<Type, Call<ResultWrapper<Type>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<ResultWrapper<Type>> = ResultCall(call)
}