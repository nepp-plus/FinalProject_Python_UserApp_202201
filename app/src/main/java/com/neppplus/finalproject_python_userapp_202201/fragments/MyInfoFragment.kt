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
import com.neppplus.finalproject_python_userapp_202201.OrderListActivity
import com.neppplus.finalproject_python_userapp_202201.R
import com.neppplus.finalproject_python_userapp_202201.ReviewListActivity
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

        binding.btnOrderList.setOnClickListener {
            val myIntent = Intent(mContext, OrderListActivity::class.java)
            startActivity(myIntent)
        }
        binding.btnReviewList.setOnClickListener {
            val myIntent = Intent(mContext, ReviewListActivity::class.java)
            startActivity(myIntent)
        }

        binding.btnLogout.setOnClickListener {

            val alert = AlertDialog.Builder(mContext)
                .setTitle("๋ก๊ทธ์์")
                .setMessage("์?๋ง ๋ก๊ทธ์์ ํ์๊ฒ?์ต๋๊น?")
                .setPositiveButton("ํ์ธ", DialogInterface.OnClickListener { dialogInterface, i ->
                    ContextUtil.setToken(mContext, "")

                    val myIntent = Intent(mContext, SplashActivity::class.java)
                    myIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(myIntent)

                    GlobalData.loginUser = null

                })
                .setNegativeButton("์ทจ์", null)

            alert.show()

        }

    }

    override fun setValues() {

        binding.txtUserName.text = GlobalData.loginUser!!.name

    }


}