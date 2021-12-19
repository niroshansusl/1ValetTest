package com.niroshan.smarthome.api.response

sealed class ResultWrapper<out T> {
    data class Success<T>(val value: T?): ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: Exception? = null): ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()
}
