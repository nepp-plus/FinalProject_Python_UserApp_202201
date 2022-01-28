package com.neppplus.finalproject_python_userapp_202201.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.finalproject_python_userapp_202201.R
import com.neppplus.finalproject_python_userapp_202201.adapters.HomeRecyclerAdapter
import com.neppplus.finalproject_python_userapp_202201.databinding.FragmentHomeBinding
import com.neppplus.finalproject_python_userapp_202201.models.BannerData
import com.neppplus.finalproject_python_userapp_202201.models.BasicResponse
import com.neppplus.finalproject_python_userapp_202201.models.ProductData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment() {

    lateinit var binding: FragmentHomeBinding

    val mBannerList = ArrayList<BannerData>()
    val mTodayHotList = ArrayList<ProductData>()
    lateinit var mHomeRecyclerAdapter : HomeRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mHomeRecyclerAdapter = HomeRecyclerAdapter(mContext, mBannerList, mTodayHotList)
        binding.homeRecyclerView.adapter = mHomeRecyclerAdapter
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(mContext)
        getHomeData()
    }

    fun getHomeData() {

        apiService.getRequestTodayHot().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {
                    val br = response.body()!!

                    mTodayHotList.clear()
                    mTodayHotList.addAll(br.data.todays_hot_lists)

                    mHomeRecyclerAdapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }

}