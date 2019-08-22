package com.example.portal.presenter

import android.util.Log
import android.widget.Toast
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception


class LoginPresenter : LogbookAPI.User, InternalStorageHelper.InternalStorageHelperInterface {

    private var loginActivity: LoginPresenterInterface
    private val logbook = LogbookAPI(this)
    private val helper: Helper = Helper()
    private val internalStorageHelper = InternalStorageHelper(this)

    constructor(loginActivity: LoginPresenterInterface){
        this.loginActivity = loginActivity
    }

    fun login(){
        loginActivity.turnOffLoginButton()
        logbook.login( object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("login", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                try{
                    val body: String? = response.body()?.string()
                    val bodyJson = helper.extractResponseJson(body)
                    val message: String = bodyJson.getString("message")
                    val code: Int = bodyJson.getInt("code")
                    loginActivity.toastMessage(message, Toast.LENGTH_SHORT)
                    loginActivity.turnOnLoginButton()
                    if(code == 200){
                        internalStorageHelper.save(getNim())
                        loginActivity.goToMain()
                    }
                }catch(e: Exception){
                    Log.e("login", e.toString())
                }
            }
        })
    }

    fun checkLogin(){
        val nim = internalStorageHelper.load().toString()
        if(nim.isNotEmpty()){
            loginActivity.goToMain()
        }
    }

    override fun getNim(): String {
        return loginActivity.getNim()
    }

    override fun getPassword(): String {
        return loginActivity.getPassword()
    }

    override fun openFileIn(name: String): FileInputStream {
        return loginActivity.openFileIn(name)
    }

    override fun openFileOut(name: String, mode: Int): FileOutputStream {
        return loginActivity.openFileOut(name, mode)
    }

    override fun getModePrivate(): Int {
        return loginActivity.getModePrivate()
    }

    override fun getFilesDir(): File {
        return loginActivity.getFilesDir()
    }

    interface LoginPresenterInterface{
        fun getNim(): String
        fun getPassword(): String
        fun toastMessage (message: String?, length: Int)
        fun turnOffLoginButton()
        fun turnOnLoginButton()
        fun goToMain()
        fun openFileIn(name: String): FileInputStream
        fun openFileOut(name: String, mode: Int): FileOutputStream
        fun getModePrivate(): Int
        fun getFilesDir(): File
    }
}