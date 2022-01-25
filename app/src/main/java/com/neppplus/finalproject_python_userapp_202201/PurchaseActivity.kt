package com.neppplus.finalproject_python_userapp_202201

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.iamport.sdk.data.sdk.IamPortRequest
import com.iamport.sdk.data.sdk.PayMethod
import com.iamport.sdk.domain.core.Iamport
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivityPurchaseBinding
import com.neppplus.finalproject_python_userapp_202201.models.BasicResponse
import com.neppplus.finalproject_python_userapp_202201.models.ShipmentInfoData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PurchaseActivity : BaseActivity() {

    lateinit var binding: ActivityPurchaseBinding

    var mSelectedShipmentInfo : ShipmentInfoData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_purchase)
        Iamport.init(this)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {


        val resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                if (it.resultCode == RESULT_OK) {
                    val dataIntent = it.data!!
                    mSelectedShipmentInfo = dataIntent.getSerializableExtra("shipment") as ShipmentInfoData
                    setSelectedShipmentInfoToUi()
                }
            }
        )

        binding.btnShipmentSelect.setOnClickListener {


            val myIntent = Intent(mContext, ShipmentInfoListActivity::class.java)
            resultLauncher.launch(myIntent)
        }

        binding.btnPay.setOnClickListener {

            val request = IamPortRequest(
                pg = "nice",                                 // PG 사
                pay_method = PayMethod.card.name,          // 결제수단
                name = "여기주문이요",                         // 주문명
                merchant_uid = "mid_123456",               // 주문번호
                amount = "3000",                           // 결제금액
                buyer_name = "홍길동"
            )
            Iamport.payment("imp16646577", iamPortRequest = request,
                approveCallback = {

                },
                paymentResultCallback = {


                }
            )
        }

    }

    override fun setValues() {

        setTitle("주문 / 결제")

    }

    override fun onResume() {
        super.onResume()


        getMyShipmentInfoList()
    }

    private fun getMyShipmentInfoList() {
        apiService.getRequestShimentInfoList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {
                    var isBasicAddressMade = false
                    response.body()!!.data.user_all_address.forEach {
                        if (it.is_basic_address) {
                            mSelectedShipmentInfo = it
                            setSelectedShipmentInfoToUi()
                            isBasicAddressMade = true
                        }
                    }

                    if (!isBasicAddressMade) {
                        binding.shipmentInfoEmptyLayout.visibility = View.VISIBLE
                        binding.btnShipmentSelect.visibility = View.GONE
                    }
                    else {
                        binding.shipmentInfoEmptyLayout.visibility = View.GONE
                        binding.btnShipmentSelect.visibility = View.VISIBLE
                    }
                }
                else {

                    binding.shipmentInfoEmptyLayout.visibility = View.VISIBLE
                    binding.btnShipmentSelect.visibility = View.GONE
                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })
    }

    private fun setSelectedShipmentInfoToUi() {
        val info = mSelectedShipmentInfo!!
        binding.txtReceiverName.text = info.name
        binding.txtAddress.text = "${info.address1} ${info.address2}"
        binding.txtPhoneNum.text = info.phone
        if (info.is_basic_address) {
            binding.txtIsBasicShipment.visibility = View.VISIBLE
        }
        else {
            binding.txtIsBasicShipment.visibility = View.GONE

        }
    }

}