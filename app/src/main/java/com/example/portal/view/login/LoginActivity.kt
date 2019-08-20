package com.example.portal.view.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.portal.R
import com.example.portal.presenter.Helper
import com.example.portal.presenter.LogbookAPI
import com.example.portal.view.main.MainActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.lang.Exception

class LoginActivity : AppCompatActivity(), LogbookAPI.User {

    private var nim: String = ""
    private var password: String = ""
    private val helper: Helper = Helper()

    override fun getNim(): String {
        return nim
    }

    override fun getPassword(): String {
        return password
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    @SuppressLint("ResourceType")
    fun login(view: View){
        nim = getUiText(R.id.nim_text)
        password = getUiText(R.id.password_text)
        val logbook = LogbookAPI()
        logbook.login(this, object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("login", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                try{
                    val body: String? = response.body?.string()
                    val bodyJson = helper.extractLoginJson(body)
                    val message: String = bodyJson.getString("message")
                    val code: Int = bodyJson.getInt("code")
                    toastMessage(message)
                    if(code == 200){
                        goToMain()
                    }
                }catch(e: Exception){
                    Log.e("login", e.toString())
                }
            }

        })
    }

    fun toastMessage (message: String) {
        helper.backgroundThreadShortToast(this, message)
    }

    @SuppressLint("ResourceType")
    fun getUiText(id: Int): String{
        return findViewById<TextView>(id).text.toString()
    }

    private fun goToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
