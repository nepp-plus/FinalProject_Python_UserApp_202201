package com.neppplus.finalproject_python_userapp_202201.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.finalproject_python_userapp_202201.LargeCategoryAdapter
import com.neppplus.finalproject_python_userapp_202201.R
import com.neppplus.finalproject_python_userapp_202201.databinding.FragmentCategoryBinding
import com.neppplus.finalproject_python_userapp_202201.models.BasicResponse
import com.neppplus.finalproject_python_userapp_202201.models.LargeCategoryData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment : BaseFragment() {

    lateinit var binding: FragmentCategoryBinding

    lateinit var mAdapter: LargeCategoryAdapter
    val mCategoryList = ArrayList<LargeCategoryData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
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

        mAdapter = LargeCategoryAdapter(mContext, mCategoryList)
        binding.largeCategoryRecyclerView.adapter = mAdapter
        binding.largeCategoryRecyclerView.layoutManager = LinearLayoutManager(mContext)

        getLargeCategoryListFromServer()
    }

    private fun getLargeCategoryListFromServer() {
        apiService.getRequestLargeCategory().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {
                    val br = response.body()!!

                    mCategoryList.clear()

                    mCategoryList.addAll(br.data.large_categories)

                    mAdapter.notifyDataSetChanged()

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })
    }


}