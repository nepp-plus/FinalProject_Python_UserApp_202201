package com.neppplus.finalproject_python_userapp_202201

import android.app.Application
import com.iamport.sdk.domain.core.Iamport

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Iamport.create(this)
    }

}