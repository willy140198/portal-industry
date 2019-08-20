package com.example.portal.presenter

import android.util.Log
import com.example.portal.view.login.LoginActivity
import okhttp3.*
import java.lang.Exception

class LogbookAPI {

    private val client = OkHttpClient()

    fun login(data:LoginActivity, callback: Callback){
        // Creating new form body
        val formBody: RequestBody = FormBody.Builder()
            .add("nim", data.getNim())
            .add("password", data.getPassword())
            .build()

        // Create request
        val request: Request = Request.Builder()
            .url("https://service.dev.dhirawigata.com/v1/logbook/login")
            .post(formBody)
            .build()

        // Execute
        client.newCall(request)
            .enqueue(callback)
    }

    interface User {
        fun getNim(): String
        fun getPassword(): String
    }
}