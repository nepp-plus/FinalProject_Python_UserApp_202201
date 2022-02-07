package com.neppplus.finalproject_python_userapp_202201.fragments.review

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.finalproject_python_userapp_202201.R
import com.neppplus.finalproject_python_userapp_202201.adapters.OrderItemAdapter
import com.neppplus.finalproject_python_userapp_202201.adapters.ReviewOrderItemAdapter
import com.neppplus.finalproject_python_userapp_202201.databinding.FragmentReviewCompleteBinding
import com.neppplus.finalproject_python_userapp_202201.fragments.BaseFragment
import com.neppplus.finalproject_python_userapp_202201.models.BasicResponse
import com.neppplus.finalproject_python_userapp_202201.models.CartData
import com.neppplus.finalproject_python_userapp_202201.models.OrderItemData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewCompleteFragment : BaseFragment() {

    lateinit var binding: FragmentReviewCompleteBinding
    val mOrderItemList = ArrayList<OrderItemData>()
    lateinit var mOrderItemAdapter: ReviewOrderItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_review_complete, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        mOrderItemAdapter = ReviewOrderItemAdapter(mContext, mOrderItemList)


    }

    override fun setValues() {

        binding.orderItemRecyclerView.adapter = mOrderItemAdapter
        binding.orderItemRecyclerView.layoutManager = LinearLayoutManager(mContext)


    }

    override fun onResume() {
        super.onResume()

        getMyReviewListFromServer()
    }

    private fun getMyReviewListFromServer() {

        apiService.getRequestMyReview(
            "작성완료"
        ).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                Log.d("리뷰 응답", response.toString())

                if (response.isSuccessful) {

                    val br = response.body()!!

                    mOrderItemList.clear()
                    mOrderItemList.addAll(br.data.user_review_list)
                    mOrderItemAdapter.notifyDataSetChanged()

                }


            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }


}