package com.example.portal.view.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.portal.R
import com.example.portal.presenter.Helper
import com.example.portal.presenter.InternalStorageHelper
import com.example.portal.presenter.LogbookAPI
import com.example.portal.presenter.LoginPresenter
import com.example.portal.view.main.MainActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

class LoginActivity : AppCompatActivity(), LoginPresenter.LoginPresenterInterface {

    private val helper: Helper = Helper()
    private lateinit var loginButton: Button
    private val loginPresenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginButton = findViewById(R.id.login_button)
        loginPresenter.checkLogin()
    }

    override fun openFileIn(name: String): FileInputStream {
        return openFileInput(name)
    }

    override fun openFileOut(name: String, mode: Int): FileOutputStream {
        return openFileOutput(name, mode)
    }

    override fun getModePrivate(): Int {
        return Context.MODE_PRIVATE
    }

    override fun getNim(): String {
        return getUiText(R.id.nim_text)
    }

    override fun getFilesDirectory(): File {
        return filesDir
    }

    override fun getPassword(): String {
        return getUiText(R.id.password_text)
    }

    override fun turnOffLoginButton() {
        loginButton.isClickable = false
        loginButton.setBackgroundColor(resources.getColor(R.color.mtrl_on_primary_disabled))
    }

    override fun turnOnLoginButton() {
        loginButton.isClickable = true
        loginButton.setBackgroundColor(resources.getColor(R.color.design_default_color_primary))
    }

    fun login(view: View){
        loginPresenter.login()
    }

    override fun toastMessage (message: String?, length: Int) {
        helper.backgroundThreadShortToast(this, message, length)
    }

    @SuppressLint("ResourceType")
    fun getUiText(id: Int): String{
        return findViewById<TextView>(id).text.toString()
    }

     override fun goToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
