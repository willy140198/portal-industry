package com.example.portal.presenter

import okhttp3.*

class LogbookAPI(data: User) {

    private val client = OkHttpClient()
    private val data: User = data

    fun login(callback: Callback){
        // Creating new form body
        val formBody: RequestBody = FormBody.Builder()
            .add("nim", data.getNim())
            .add("password", data.getPassword())
            .build()

        // Create request
        val url = "https://service.dev.dhirawigata.com/v1/logbook/login"
        val request: Request = requestBuilder(url, formBody)

        // Execute
        client.newCall(request)
            .enqueue(callback)
    }

    fun check(callback: Callback){
        // Creating new form body
        val formBody: RequestBody = FormBody.Builder()
            .add("nim", data.getNim())
            .build()

        // Create request
        val url = "https://service.dev.dhirawigata.com/v1/logbook/check"
        val request: Request = requestBuilder(url, formBody)

        // Execute
        client.newCall(request)
            .enqueue(callback)
    }

    private fun requestBuilder(url: String, formBody: RequestBody):Request {
        return Request.Builder()
            .url(url)
            .post(formBody)
            .build()
    }

    interface User {
        fun getNim(): String
        fun getPassword(): String
    }
}