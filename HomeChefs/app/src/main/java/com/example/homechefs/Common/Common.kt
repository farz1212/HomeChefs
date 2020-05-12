package com.example.homechefs.Common

import com.example.homechefs.Remote.IMyAPI
import com.example.homechefs.Remote.RetrofitClient

object Common {
    val BASE_URL = "http://192.168.0.10/myapi/"
    val api : IMyAPI
    get() = RetrofitClient.getClient(BASE_URL).create(IMyAPI::class.java)
}