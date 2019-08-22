package com.example.portal.presenter

import android.content.Context
import android.os.Handler
import android.widget.Toast
import android.os.Looper
import android.util.Log
import org.json.JSONObject


class Helper {
    fun backgroundThreadShortToast(context: Context, msg: String?, length: Int) {
        if (context != null && msg != null) {
            Handler(Looper.getMainLooper())
                .post { Toast.makeText(context, msg, length).show() }
        }
    }

    fun extractResponseJson(body: String?): JSONObject{
        val bodyJson = JSONObject(body)
        val result = JSONObject()
        var message: String
        val code: String
        if(bodyJson.has("meta")){
            val temp = bodyJson.getJSONObject("meta")
            message = temp.getString("msg")
            code = temp.getString("code")
        }else{
            message = bodyJson.getString("message")
            code = bodyJson.getString("statusCode")
        }
        result.put("message", message)
        result.put("code", code)
        return result
    }
}