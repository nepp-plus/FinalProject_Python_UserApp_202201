package com.neppplus.finalproject_python_userapp_202201

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.iamport.sdk.data.sdk.IamPortRequest
import com.iamport.sdk.data.sdk.PayMethod
import com.iamport.sdk.domain.core.Iamport
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivityPurchaseBinding
import com.neppplus.finalproject_python_userapp_202201.models.BasicResponse
import com.neppplus.finalproject_python_userapp_202201.models.ShipmentInfoData
import com.neppplus.finalproject_python_userapp_202201.utils.GlobalData
import okhttp3.internal.and
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder
import java.security.MessageDigest
import java.util.*

class PurchaseActivity : BaseActivity() {

    lateinit var binding: ActivityPurchaseBinding

    var mSelectedShipmentInfo : ShipmentInfoData? = null

    var mBuyProductJsonStr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_purchase)
        Iamport.init(this)
        mBuyProductJsonStr = intent.getStringExtra("buyInfoJson").toString()
        Log.d("구매할상품들", mBuyProductJsonStr)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.shipmentOptionsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                binding.edtShipmentOptions.visibility = if (position == 4) {
                    View.VISIBLE
                }
                else {
                    View.GONE
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.btnAddShipmentInfo.setOnClickListener {

            val myIntent = Intent(mContext, EditShipmentInfoActivity::class.java)
            startActivity(myIntent)
        }


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

            if (mSelectedShipmentInfo == null) {
                Toast.makeText(mContext, "선택된 배송지가 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val shipmentOption = if (binding.shipmentOptionsSpinner.selectedItemPosition == 4) {
                binding.edtShipmentOptions.text.toString()
            }
            else {
                binding.shipmentOptionsSpinner.selectedItem.toString()
            }

            val userEmail = GlobalData.loginUser!!.email
            val now = Date().time

            val shipmentInfo = mSelectedShipmentInfo!!
            apiService.postRequestOrder(
                shipmentInfo.name,
                "${shipmentInfo.address1} ${shipmentInfo.address2}",
                shipmentInfo.zipcode,
                shipmentInfo.phone,
                shipmentOption,
                "testimpuid1234",
                "${userEmail}_${now}",
                mBuyProductJsonStr

            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                    if (response.isSuccessful) {

                        Toast.makeText(mContext, "구매가 완료되었습니다.", Toast.LENGTH_SHORT).show()

                    }
                    else {

                        Toast.makeText(mContext, "구매에 실패했습니다.", Toast.LENGTH_SHORT).show()

                        val jsonObj = JSONObject(response.errorBody()!!.string())
                        Log.d("구매완료에러", jsonObj.toString())

                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })


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
////                  결제 성공시 -> [ {'product_id':3 , 'quantity': 10} , {'product_id':5 , 'quantity': 2}, {'product_id':8 , 'quantity': 1} ] 문구 전송
//
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

    fun byteToHexString(data: ByteArray): String {
        val sb = StringBuilder()
        for (b in data) {
            sb.append(Integer.toString((b and 0xff) + 0x100, 16).substring(1))
        }
        return sb.toString()
    }


}