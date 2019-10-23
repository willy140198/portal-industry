package com.example.portal.presenter

import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class MainPresenter(mainActivity: MainPresenterInterface) : LogbookAPI.User,
    InternalStorageHelper.InternalStorageHelperInterface {

    private val logbook = LogbookAPI(this)
    private val mainActivity = mainActivity
    private val internalStorageHelper = InternalStorageHelper(this)
    private val helper = Helper()

    fun checkLogin(){
        logbook.check(object: Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val body: String? = response.body()?.string()
                    val bodyJson = helper.extractResponseJson(body)
                    val code: Int = bodyJson.getInt("code")
                    if(code != 200){
                        mainActivity.toastMessage("Session has expired", Toast.LENGTH_SHORT)
                        deleteStorage()
                        mainActivity.goToLogin()
                    }
                }catch (e: Exception){
                    Log.e("login", e.toString())
                }
            }
        })
    }

    fun deleteStorage(){
        internalStorageHelper.delete()
    }

    override fun getNim(): String {
        return internalStorageHelper.load()
    }

    override fun getPassword(): String {
        return ""
    }

    override fun openFileIn(name: String): FileInputStream {
        return mainActivity.openFileIn(name)
    }

    override fun openFileOut(name: String, mode: Int): FileOutputStream {
        return mainActivity.openFileOut(name, mode)
    }

    override fun getModePrivate(): Int {
        return mainActivity.getModePrivate()
    }

    override fun getFilesDirectory(): File {
        return mainActivity.getFilesDirectory()
    }

    interface MainPresenterInterface{
        fun toastMessage (message: String?, length: Int)
        fun openFileIn(name: String): FileInputStream
        fun openFileOut(name: String, mode: Int): FileOutputStream
        fun getModePrivate(): Int
        fun getFilesDirectory(): File
        fun goToLogin()
    }
}