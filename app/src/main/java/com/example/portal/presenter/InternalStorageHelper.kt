package com.example.portal.presenter

import android.util.Log
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder

class InternalStorageHelper (activity: InternalStorageHelperInterface) {

    private val fileName: String = "logbook.txt"
    private var activity: InternalStorageHelperInterface? = activity

    fun save(nim: String){
        var fos: FileOutputStream? = null

        try {
            fos = activity?.openFileOut(fileName, activity!!.getModePrivate())
            fos?.write(nim.toByteArray())
        }catch (e: Exception){
            Log.e("login", e.toString())
        }finally {
            fos?.close()
        }
    }

    fun load(): String{
        var fis: FileInputStream? = null
        var text = ""
        try{
            fis = activity?.openFileIn(fileName)
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
        val dir = activity?.getFilesDirectory()
        val file = File(dir, fileName)
        file.delete()
    }

    interface InternalStorageHelperInterface{
        fun openFileIn(name: String):FileInputStream
        fun openFileOut(name: String, mode: Int):FileOutputStream
        fun getModePrivate(): Int
        fun getFilesDirectory(): File
    }
}