package com.neppplus.finalproject_python_userapp_202201

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.finalproject_python_userapp_202201.adapters.SmallCategorySpinnerAdapter
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivityViewCategoryDetailBinding
import com.neppplus.finalproject_python_userapp_202201.models.BasicResponse
import com.neppplus.finalproject_python_userapp_202201.models.LargeCategoryData
import com.neppplus.finalproject_python_userapp_202201.models.SmallCategoryData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewCategoryDetailActivity : BaseActivity() {

    lateinit var binding: ActivityViewCategoryDetailBinding

    lateinit var mLargeCategoryData: LargeCategoryData

    val mSmallCategoryList = ArrayList<SmallCategoryData>()
    lateinit var mAdapter: SmallCategorySpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_category_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mLargeCategoryData = intent.getSerializableExtra("category") as LargeCategoryData

        setTitle(mLargeCategoryData.name)

        mAdapter = SmallCategorySpinnerAdapter(mContext, mSmallCategoryList)
        binding.smallCategorySpinner.adapter = mAdapter

        getSmallCagetoryList()
    }

    private fun getSmallCagetoryList() {
        apiList.getRequestSmallCategory(
            mLargeCategoryData.id,
        ).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    val br = response.body()!!

                    mSmallCategoryList.clear()
                    mSmallCategoryList.addAll(br.data.small_categories)

                    mAdapter.notifyDataSetChanged()

                }
                else {
                    Toast.makeText(mContext, "준비중인 카테고리 입니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })
    }
}