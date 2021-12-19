package com.niroshan.smarthome.internal

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.niroshan.smarthome.api.response.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

fun ImageView.setAvatarImage(view: View, url: String) {
    Glide.with(view)
        .load(url)
        .centerCrop()
        .error(android.R.drawable.stat_notify_error)
        .into(this)
}

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    ResultWrapper.GenericError(code)
                }
                else -> {
                    ResultWrapper.GenericError(null, null)
                }
            }
        }
    }
}