package com.example.portal.view.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import com.example.portal.R
import com.example.portal.presenter.Helper
import com.example.portal.presenter.InternalStorageHelper
import com.example.portal.presenter.MainPresenter
import com.example.portal.view.login.LoginActivity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity(), MainPresenter.MainPresenterInterface {

    private val helper: Helper = Helper()
    private val mainPresenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.app_bar)
        setSupportActionBar(toolbar)
        mainPresenter.checkLogin()
    }

    private fun logout(){
        mainPresenter.deleteStorage()
        goToLogin()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.setting_item -> ""
            R.id.logout_item -> logout()
        }
        return true
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

    override fun getFilesDirectory(): File {
        return filesDir
    }

    override fun toastMessage(message: String?, length: Int) {
        helper.backgroundThreadShortToast(this, message, length)
    }

    override fun goToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}
