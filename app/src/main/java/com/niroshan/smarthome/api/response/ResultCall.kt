package com.niroshan.smarthome.api.response

import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, ResultWrapper<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<ResultWrapper<T>>) = proxy.enqueue(object: Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val code = response.code()
            val result = if (code in 200 until 300) {
                val body = response.body()
                val successResult:ResultWrapper<T> = ResultWrapper.Success(body)
                successResult
            } else {
                ResultWrapper.GenericError(code)
            }

            callback.onResponse(this@ResultCall, Response.success(result))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            val result = if (t is IOException) {
                ResultWrapper.NetworkError
            } else {
                ResultWrapper.GenericError(null)
            }

            callback.onResponse(this@ResultCall, Response.success(result))
        }
    })

    override fun cloneImpl() = ResultCall(proxy.clone())
    override fun timeout(): Timeout {
        TODO("Not yet implemented")
    }
}