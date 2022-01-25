package com.neppplus.finalproject_python_userapp_202201

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
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
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

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

            alert.setView(customView)

            val dialog = alert.show()

//            val urlBuilder = "https://dapi.kakao.com/v2/local/search/address.json".toHttpUrlOrNull()!!.newBuilder()
//            urlBuilder.addEncodedQueryParameter("query")

        }

    }

    override fun setValues() {

    }
}