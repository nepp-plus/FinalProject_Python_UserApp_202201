package com.neppplus.finalproject_python_userapp_202201.fragments

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.neppplus.finalproject_python_userapp_202201.R
import com.neppplus.finalproject_python_userapp_202201.SplashActivity
import com.neppplus.finalproject_python_userapp_202201.databinding.FragmentHomeBinding
import com.neppplus.finalproject_python_userapp_202201.databinding.FragmentMyInfoBinding
import com.neppplus.finalproject_python_userapp_202201.utils.ContextUtil
import com.neppplus.finalproject_python_userapp_202201.utils.GlobalData

class MyInfoFragment : BaseFragment() {

    lateinit var binding: FragmentMyInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_info, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnLogout.setOnClickListener {

            val alert = AlertDialog.Builder(mContext)
                .setTitle("로그아웃")
                .setMessage("정말 로그아웃 하시겠습니까?")
                .setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                    ContextUtil.setToken(mContext, "")

                    val myIntent = Intent(mContext, SplashActivity::class.java)
                    myIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(myIntent)

                    GlobalData.loginUser = null

                })
                .setNegativeButton("취소", null)

            alert.show()

        }

    }

    override fun setValues() {

        binding.txtUserName.text = GlobalData.loginUser!!.name

    }


}