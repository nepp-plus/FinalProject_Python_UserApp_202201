package com.neppplus.finalproject_python_userapp_202201

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.finalproject_python_userapp_202201.adapters.AddressSelectRecyclerAdapter
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivityEditShipmentInfoBinding
import com.neppplus.finalproject_python_userapp_202201.models.AddressData
import com.neppplus.finalproject_python_userapp_202201.models.ShipmentInfoData
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class EditShipmentInfoActivity : BaseActivity() {

    lateinit var binding: ActivityEditShipmentInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_shipment_info)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        val resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                if (it.resultCode == RESULT_OK) {
                    val dataIntent = it.data!!
                }
            }
        )

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

                    dialog.dismiss()
                }

            }


        }

    }

    override fun setValues() {

    }
}