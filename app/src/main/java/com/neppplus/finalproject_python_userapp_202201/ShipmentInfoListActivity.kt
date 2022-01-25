package com.neppplus.finalproject_python_userapp_202201

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.finalproject_python_userapp_202201.adapters.ShipmentInfoAdapter
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivityShipmentInfoListBinding
import com.neppplus.finalproject_python_userapp_202201.models.BasicResponse
import com.neppplus.finalproject_python_userapp_202201.models.ShipmentInfoData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShipmentInfoListActivity : BaseActivity() {

    lateinit var binding: ActivityShipmentInfoListBinding

    val mShipmentInfoList = ArrayList<ShipmentInfoData>()
    lateinit var mAdapter: ShipmentInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shipment_info_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        mAdapter = ShipmentInfoAdapter(mContext, mShipmentInfoList)
        mAdapter.setOnItemClick(object : ShipmentInfoAdapter.OnItemClick {
            override fun onItemClick(position: Int) {

                val clickedShipmentInfo = mShipmentInfoList[position]

                val resultIntent = Intent()
                resultIntent.putExtra("shipment", clickedShipmentInfo)
                setResult(RESULT_OK, resultIntent)
                finish()

            }

        })

    }

    override fun setValues() {

        binding.shipmentInfoRecyclerView.adapter = mAdapter
        binding.shipmentInfoRecyclerView.layoutManager = LinearLayoutManager(mContext)

    }

    override fun onResume() {
        super.onResume()

        getMyShipmentInfoList()

    }

    private fun getMyShipmentInfoList() {
        apiService.getRequestShimentInfoList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {
                    mShipmentInfoList.clear()
                    mShipmentInfoList.addAll(response.body()!!.data.user_all_address)
                    mAdapter.notifyDataSetChanged()
                }
                else {

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })
    }
}