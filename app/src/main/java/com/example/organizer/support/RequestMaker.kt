package com.example.organizer.support

import okhttp3.OkHttpClient
import okhttp3.Request

class RequestMaker {

    private val client = OkHttpClient()

    fun makeRequest(url: String) = run {
        val request = Request.Builder()
            .url(url)
            .build()

        val response = client.newCall(request).execute()

        response.body()!!.string()
    }

}