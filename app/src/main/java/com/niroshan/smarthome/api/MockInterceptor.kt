package com.niroshan.smarthome.api

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.ResponseBody

class MockInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val response = when (chain.request().url.encodedPath) {
            "/deviceList" -> """[
{
    "id": 1234,
    "type": "Sensor",
    "price": 20,
    "currency": "USD",
    "isFavorite": false,
    "imageUrl": "https://cdn.pixabay.com/photo/2019/12/18/12/17/fingerprint-4703841_1280.jpg",
    "title": "Test Sensor",
    "description": ""
  },
{
    "id": 1235,
    "type": "Thermostat",
    "price": 25,
    "currency": "USD",
    "isFavorite": false,
    "imageUrl": "https://cdn.pixabay.com/photo/2014/09/27/16/59/heating-463904_1280.jpg",
    "title": "Test Thermostat",
    "description": ""
  },
{
    "id": 1236,
    "type": "Light Sensor",
    "price": 10,
    "currency": "USD",
    "isFavorite": false,
    "imageUrl": "https://www.telegraph.co.uk/content/dam/recommended/2018/12/05/best-smart-lightbulbs_review_trans_NvBQzQNjv4BqM37qcIWR9CtrqmiMdQVx7FISlvedRljPTE46L2CJIz8.png?imwidth=960",
    "title": "Test Light 01",
    "description": ""
  },
{
    "id": 1237,
    "type": "Light Sensor",
    "price": 10,
    "currency": "USD",
    "isFavorite": false,
    "imageUrl": "https://s3.ap-southeast-1.amazonaws.com/images.asianage.com/images/aa-Cover-otm0j7lrjgpru9ncb742l80eu7-20190530135821.Medi.jpeg",
    "title": "Test Light 02",
    "description": ""
  },
{
    "id": 1238,
    "type": "Sensor",
    "price": 50,
    "currency": "USD",
    "isFavorite": false,
    "imageUrl": "https://cdn.pixabay.com/photo/2020/03/05/17/36/smart-home-4905021_1280.jpg",
    "title": "Test Smart Lock",
    "description": ""
  },
{
    "id": 1239,
    "type": "Sensor",
    "price": 30,
    "currency": "USD",
    "isFavorite": false,
    "imageUrl": "https://www.enggcyclopedia.com/wp-content/uploads/2011/11/Smoke_detector.jpg",
    "title": "Test Smock Ditector",
    "description": ""
  }

]"""
            else -> throw Error("unknown request")
        }

        val mediaType = "application/json".toMediaTypeOrNull()
        val responseBody = ResponseBody.create(mediaType, response)

        return okhttp3.Response.Builder()
            .protocol(Protocol.HTTP_1_0)
            .request(chain.request())
            .code(200)
            .message("")
            .body(responseBody)
            .build()
    }
}