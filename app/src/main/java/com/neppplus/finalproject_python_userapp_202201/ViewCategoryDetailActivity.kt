package com.neppplus.finalproject_python_userapp_202201

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.finalproject_python_userapp_202201.adapters.ProductAdapter
import com.neppplus.finalproject_python_userapp_202201.adapters.SmallCategorySpinnerAdapter
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivityViewCategoryDetailBinding
import com.neppplus.finalproject_python_userapp_202201.models.BasicResponse
import com.neppplus.finalproject_python_userapp_202201.models.LargeCategoryData
import com.neppplus.finalproject_python_userapp_202201.models.ProductData
import com.neppplus.finalproject_python_userapp_202201.models.SmallCategoryData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewCategoryDetailActivity : BaseActivity() {

    lateinit var binding: ActivityViewCategoryDetailBinding

    lateinit var mLargeCategoryData: LargeCategoryData

    val mSmallCategoryList = ArrayList<SmallCategoryData>()
    lateinit var mSmallCategorySpinnerAdapter: SmallCategorySpinnerAdapter

    val mProductList = ArrayList<ProductData>()
    lateinit var mProductsAdapter: ProductAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_category_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        mProductsAdapter = ProductAdapter(mContext, mProductList)

        mProductsAdapter.setOnItemClick(object : ProductAdapter.OnItemClick {
            override fun onItemClick(position: Int) {

                val clickedProduct = mProductList[position]

                val myIntent = Intent(mContext, ViewProductDetailActivity::class.java)
                myIntent.putExtra("product", clickedProduct)
                startActivity(myIntent)
            }

        })

        binding.smallCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val selectedSmallCategoryData = mSmallCategoryList[position]

                apiService.getRequestProductsBySmallCategory(
                    selectedSmallCategoryData.id
                ).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        mProductList.clear()

                        if (response.isSuccessful) {
                            val br = response.body()!!
                            mProductList.addAll(br.data.products)
                        }
                        else {

                        }

                        mProductsAdapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }

                })
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

    }

    override fun setValues() {
        mLargeCategoryData = intent.getSerializableExtra("category") as LargeCategoryData

        setTitle(mLargeCategoryData.name)

        mSmallCategorySpinnerAdapter = SmallCategorySpinnerAdapter(mContext, mSmallCategoryList)
        binding.smallCategorySpinner.adapter = mSmallCategorySpinnerAdapter

        binding.productRecyclerView.adapter = mProductsAdapter
        binding.productRecyclerView.layoutManager = LinearLayoutManager(mContext)

        getSmallCagetoryList()
    }

    private fun getSmallCagetoryList() {
        apiService.getRequestSmallCategory(
            mLargeCategoryData.id,
        ).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    val br = response.body()!!

                    mSmallCategoryList.clear()
                    mSmallCategoryList.add(SmallCategoryData(0, "전체", 0))
                    mSmallCategoryList.addAll(br.data.small_categories)

                    mSmallCategorySpinnerAdapter.notifyDataSetChanged()

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