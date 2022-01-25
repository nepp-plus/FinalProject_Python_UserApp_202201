package com.neppplus.finalproject_python_userapp_202201

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.neppplus.finalproject_python_userapp_202201.api.ServerAPI
import com.neppplus.finalproject_python_userapp_202201.api.ServerAPIInterface

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext: Context

    lateinit var apiService: ServerAPIInterface


    lateinit var txtTitle: TextView
    lateinit var btnAdd : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        val retrofit = ServerAPI.getRetrofit(mContext)
        apiService = retrofit.create(ServerAPIInterface::class.java)


        supportActionBar?.let {
            setCustomActionBar()
        }
    }

    override fun setTitle(title: CharSequence?) {
//        super.setTitle(title)

        txtTitle.text = title

    }

    abstract fun setupEvents()
    abstract fun setValues()

    private fun setCustomActionBar() {

        val defaultActionBar = supportActionBar!!
        defaultActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        defaultActionBar.setCustomView(R.layout.custom_action_bar)
        val toolBar =  defaultActionBar.customView.parent as Toolbar
        toolBar.setContentInsetsAbsolute(0, 0)

        val customBar = defaultActionBar.customView

        txtTitle = customBar.findViewById(R.id.txtTitle)
        btnAdd = customBar.findViewById(R.id.btnAdd)

    }

}