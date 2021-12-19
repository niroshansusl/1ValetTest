package com.niroshan.smarthome

import com.niroshan.smarthome.api.response.ResultWrapper
import com.niroshan.smarthome.internal.safeApiCall
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.junit.Assert.*
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class NetworkHelperTest {
    private val dispatcher = TestCoroutineDispatcher()

    @Test
    fun `when lambda returns successfully then it should emit the result as success`() {
        runBlockingTest {
            val lambdaResult = true
            val result = safeApiCall(dispatcher) { lambdaResult }
            assertEquals(ResultWrapper.Success(lambdaResult), result)
        }
    }

    @Test
    fun `when lambda throws IOException then it should emit the result as NetworkError`() {
        runBlockingTest {
            val result = safeApiCall(dispatcher) { throw IOException() }
            assertEquals(ResultWrapper.NetworkError, result)
        }
    }

    @Test
    fun `when lambda throws HttpException then it should emit the result as GenericError`() {
        val errorBody = "{\"errors\": [\"Unexpected parameter\"]}".toResponseBody("application/json".toMediaTypeOrNull())

        runBlockingTest {
            val result = safeApiCall(dispatcher) {
                throw HttpException(Response.error<Any>(422, errorBody))
            }
            assertEquals(ResultWrapper.GenericError(422), result)
        }
    }

    @Test
    fun `when lambda throws unknown exception then it should emit GenericError`() {
        runBlockingTest {
            val result = safeApiCall(dispatcher) {
                throw IllegalStateException()
            }
            assertEquals(ResultWrapper.GenericError(), result)
        }
    }
}