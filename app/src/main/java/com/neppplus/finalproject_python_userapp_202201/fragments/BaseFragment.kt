package com.neppplus.finalproject_python_userapp_202201.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.neppplus.finalproject_python_userapp_202201.api.ServerAPI
import com.neppplus.finalproject_python_userapp_202201.api.ServerAPIInterface

abstract class BaseFragment : Fragment() {
    lateinit var mContext: Context

    lateinit var apiService: ServerAPIInterface

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = requireContext()

        val retrofit = ServerAPI.getRetrofit(mContext)
        apiService = retrofit.create(ServerAPIInterface::class.java)

    }

    abstract fun setupEvents()
    abstract fun setValues()
}