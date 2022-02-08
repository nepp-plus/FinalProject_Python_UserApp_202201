package com.neppplus.finalproject_python_userapp_202201

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
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

    var purchaseAmount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_purchase)
        Iamport.init(this)
        mBuyProductJsonStr = intent.getStringExtra("buyInfoJson").toString()
        purchaseAmount = intent.getIntExtra("purchaseAmount", 0)
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

            val selectedPayMethodIndex = binding.payMethodRadioGroup.indexOfChild(findViewById(binding.payMethodRadioGroup.checkedRadioButtonId))

            if (selectedPayMethodIndex == -1) {

                Toast.makeText(mContext, "선택된 결제 방법이 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val payMethod = binding.payMethodRadioGroup.getChildAt(selectedPayMethodIndex)!!.tag.toString()

            val mid = "${userEmail}_${now}"

            val shipmentInfo = mSelectedShipmentInfo!!

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("테스트 결제 안내")
            alert.setMessage("본 결제는 테스트 결제로, 다음날 자동으로 취소됩니다. 실제 물건이 배송되지 않음을 유의 바랍니다.")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->

                val request = IamPortRequest(
                    pg = "nice",                                 // PG 사
                    pay_method = payMethod,          // 결제수단
                    name = "일반 상품 구매",                         // 주문명
                    merchant_uid = mid,               // 주문번호
                    amount = purchaseAmount.toString(),                           // 결제금액
                    buyer_name = GlobalData.loginUser!!.name,
                    buyer_email = GlobalData.loginUser!!.email,
                )
                Iamport.payment("imp16646577", iamPortRequest = request,
                    approveCallback = {


                    },
                    paymentResultCallback = {

                        Log.d("paymentResultCallback", it.toString())

                        it?.let {
                            val impUid = it.imp_uid!!

                            apiService.postRequestOrder(
                                shipmentInfo.name,
                                "${shipmentInfo.address1} ${shipmentInfo.address2}",
                                shipmentInfo.zipcode,
                                shipmentInfo.phone,
                                shipmentOption,
                                impUid,
                                mid,
                                mBuyProductJsonStr

                            ).enqueue(object : Callback<BasicResponse> {
                                override fun onResponse(
                                    call: Call<BasicResponse>,
                                    response: Response<BasicResponse>
                                ) {

                                    if (response.isSuccessful) {

                                        Toast.makeText(mContext, "구매가 완료되었습니다.", Toast.LENGTH_SHORT).show()

                                        finish()
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


                        }

                        if (it == null) {
                            Toast.makeText(mContext, "결제에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }


                    }
                )
            })

            alert.show()

        }


    }

    override fun setValues() {

        setTitle("주문 / 결제")


    }

    override fun onResume() {
        super.onResume()
        if (mSelectedShipmentInfo == null) {

            getMyShipmentInfoList()

        }
    }

    private fun getMyShipmentInfoList() {
        apiService.getRequestShimentInfoList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {
                    val br = response.body()!!

                    if (mSelectedShipmentInfo == null) {
                        if (br.data.basic_address == null) {
                            binding.shipmentInfoEmptyLayout.visibility = View.VISIBLE
                            binding.btnShipmentSelect.visibility = View.GONE
                        }
                        else {
                            mSelectedShipmentInfo = br.data.basic_address
                            setSelectedShipmentInfoToUi()
                            binding.shipmentInfoEmptyLayout.visibility = View.GONE
                            binding.btnShipmentSelect.visibility = View.VISIBLE
                        }
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