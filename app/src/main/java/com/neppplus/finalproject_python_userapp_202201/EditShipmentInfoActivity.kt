package com.neppplus.finalproject_python_userapp_202201

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.finalproject_python_userapp_202201.adapters.AddressSelectRecyclerAdapter
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivityEditShipmentInfoBinding
import com.neppplus.finalproject_python_userapp_202201.models.AddressData
import com.neppplus.finalproject_python_userapp_202201.models.BasicResponse
import com.neppplus.finalproject_python_userapp_202201.models.ShipmentInfoData
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class EditShipmentInfoActivity : BaseActivity() {

    lateinit var binding: ActivityEditShipmentInfoBinding

    var mSelectedAddressData : AddressData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_shipment_info)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnSave.setOnClickListener {

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("배송지 저장")
            alert.setMessage("정말 배송지를 저장 하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->

                val inputName = binding.edtReceiverName.text.toString()
                if (inputName.length < 2) {
                    Toast.makeText(mContext, "이름은 최소 2자 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }

                if (mSelectedAddressData == null) {
                    Toast.makeText(mContext, "배송지 주소를 입력해주세요.", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }

                val inputAddress1 = binding.txtAddress1.text.toString()
                val inputAddress2 = binding.edtAddress2.text.toString()

                val inputPhone = binding.edtPhone.text.toString()

                if (inputPhone.length < 5) {
                    Toast.makeText(mContext, "연락처는 최소 5자 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }

                val isBasicShipmentInfo = binding.basicAddressCheckBox.isChecked

                val addressData = mSelectedAddressData!!
                apiService.postRequestAddShipmentInfo(
                    inputName,
                    inputPhone,
                    addressData.zipCode,
                    inputAddress1,
                    inputAddress2,
                    isBasicShipmentInfo,
                    ""

                ).enqueue(object : retrofit2.Callback<BasicResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<BasicResponse>,
                        response: retrofit2.Response<BasicResponse>
                    ) {

                        if (response.isSuccessful) {
                            Toast.makeText(mContext, "배송지 저장에 성공했습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        }

                    }

                    override fun onFailure(call: retrofit2.Call<BasicResponse>, t: Throwable) {

                    }

                })

            })
            alert.setNegativeButton("취소", null)
            alert.show()

        }

        binding.btnSearchAddress.setOnClickListener {

            val alert = AlertDialog.Builder(mContext)
            val customView = LayoutInflater.from(mContext)
                .inflate(R.layout.my_custom_alert_select_address, null)
            val addressSelectRecyclerView =
                customView.findViewById<RecyclerView>(R.id.addressSelectRecyclerView)

            val addressList = ArrayList<AddressData>()
            val addressAdapter = AddressSelectRecyclerAdapter(mContext, addressList)
            addressSelectRecyclerView.adapter = addressAdapter
            addressSelectRecyclerView.layoutManager = LinearLayoutManager(mContext)


            val edtAddress1Keyword = customView.findViewById<EditText>(R.id.edtAddress1Keyword)
            val btnSearch = customView.findViewById<Button>(R.id.btnSearch)

            btnSearch.setOnClickListener {

                val inputKeyword = edtAddress1Keyword.text.toString()


                val urlBuilder = "https://dapi.kakao.com/v2/local/search/address.json".toHttpUrlOrNull()!!.newBuilder()
                urlBuilder.addEncodedQueryParameter("query", inputKeyword)
                urlBuilder.addEncodedQueryParameter("size", 30.toString())

                val request = Request.Builder()
                    .url(urlBuilder.toString())
                    .get()
                    .header("Authorization", "KakaoAK 74c28e8fb2324f6c693626ce6972288f")
                    .build()

                val client = OkHttpClient()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {

                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) {

                            val jsonObj = JSONObject(response.body!!.string())
                            Log.d("jsonObj", jsonObj.toString())

                            addressList.clear()
                            val documentsArr = jsonObj.getJSONArray("documents")
                            for (i in  0 until  documentsArr.length()) {

                                val documentObj = documentsArr.getJSONObject(i)
                                val addressData = AddressData()
                                if (!documentObj.isNull("address")) {
                                    val addressObj = documentObj.getJSONObject("address")
                                    addressData.oldAddress = addressObj.getString("address_name")
                                }
                                else {
                                    addressData.oldAddress = "지번 주소 없음"
                                }

                                val roadAddressObj = documentObj.getJSONObject("road_address")

                                addressData.roadAddress = roadAddressObj.getString("address_name")

                                if (roadAddressObj.getString("building_name") != "") {
                                    addressData.roadAddress += (" " + roadAddressObj.getString("building_name"))
                                }

                                addressData.zipCode = roadAddressObj.getString("zone_no")

                                if (addressData.zipCode != "") {
                                    addressList.add(addressData)
                                }



                            }

                            runOnUiThread {
                                if (addressList.isEmpty()) {
                                    Toast.makeText(mContext, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show()
                                }
                                addressAdapter.notifyDataSetChanged()
                            }


                        }
                    }

                })

            }

            alert.setView(customView)

            val dialog = alert.show()


            addressAdapter.onItemClickListener = object : AddressSelectRecyclerAdapter.OnItemClickListener {
                override fun onItemClick(data: AddressData) {

                    mSelectedAddressData = data
                    binding.address2Layout.visibility = View.VISIBLE
                    binding.txtAddress1.text = data.roadAddress
                    binding.txtAddress1.setTextColor(ContextCompat.getColor(mContext, R.color.black))

                    dialog.dismiss()
                }

            }


        }

    }

    override fun setValues() {

    }
}