package com.example.portal.presenter

import android.util.Log
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder

class InternalStorageHelper {
    private val fileName: String = "logbook.txt"
    private var loginActivity: InternalStorageHelperInterface?

    constructor(loginActivity: InternalStorageHelperInterface){
        this.loginActivity = loginActivity
    }

    fun save(nim: String){
        var fos: FileOutputStream? = null

        try {
            fos = loginActivity?.openFileOut(fileName, loginActivity!!.getModePrivate())
            fos?.write(nim.toByteArray())
        }catch (e: Exception){
            Log.e("login", e.toString())
        }finally {
            fos?.close()
        }
    }

    fun load(): String?{
        var fis: FileInputStream? = null
        var text: String? = null
        try{
            fis = loginActivity?.openFileIn(fileName)
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            val sb = StringBuilder()
            text= br.readLine()

            if(text != null){
                text = sb.append(text).toString()
            }
        }catch (e: Exception){
            Log.e("login", e.toString())
        }finally {
            fis?.close()
        }
        return text
    }

    fun delete(){
        val dir = loginActivity?.getFilesDir()
        val file = File(dir, fileName)
        file.delete()
    }

    interface InternalStorageHelperInterface{
        fun openFileIn(name: String):FileInputStream
        fun openFileOut(name: String, mode: Int):FileOutputStream
        fun getModePrivate(): Int
        fun getFilesDir(): File
    }
}