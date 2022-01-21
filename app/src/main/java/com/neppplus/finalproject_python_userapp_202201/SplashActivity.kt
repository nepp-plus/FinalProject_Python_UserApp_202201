package com.neppplus.finalproject_python_userapp_202201

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }
}