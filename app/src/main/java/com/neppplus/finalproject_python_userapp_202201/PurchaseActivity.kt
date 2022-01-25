package com.neppplus.finalproject_python_userapp_202201

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivityPurchaseBinding

class PurchaseActivity : BaseActivity() {

    lateinit var binding: ActivityPurchaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_purchase)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnPay.setOnClickListener {



        }

    }

    override fun setValues() {

        setTitle("주문 / 결제")

    }
}