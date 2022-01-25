package com.neppplus.finalproject_python_userapp_202201

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.iamport.sdk.data.sdk.IamPortRequest
import com.iamport.sdk.data.sdk.PayMethod
import com.iamport.sdk.domain.core.Iamport
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivityPurchaseBinding

class PurchaseActivity : BaseActivity() {

    lateinit var binding: ActivityPurchaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_purchase)
        Iamport.init(this)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnPay.setOnClickListener {

//            val request = IamPortRequest(
//                pg = "nice",                                 // PG 사
//                pay_method = PayMethod.card.name,          // 결제수단
//                name = "여기주문이요",                         // 주문명
//                merchant_uid = "mid_123456",               // 주문번호
//                amount = "3000",                           // 결제금액
//                buyer_name = "홍길동"
//            )
//            Iamport.payment("imp16646577", iamPortRequest = request,
//                approveCallback = {
//
//                },
//                paymentResultCallback = {
//
//
//                }
//            )
        }

    }

    override fun setValues() {

        setTitle("주문 / 결제")

    }
}