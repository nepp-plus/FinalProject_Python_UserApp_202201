package com.neppplus.finalproject_python_userapp_202201

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.neppplus.finalproject_python_userapp_202201.adapters.ImageSlideAdapter
import com.neppplus.finalproject_python_userapp_202201.adapters.OptionValuesSpinnerAdapter
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivityViewProductDetailBinding
import com.neppplus.finalproject_python_userapp_202201.fragments.product.ProductDetailFragment
import com.neppplus.finalproject_python_userapp_202201.fragments.product.ProductReviewListFragment
import com.neppplus.finalproject_python_userapp_202201.fragments.review.ReviewCompleteFragment
import com.neppplus.finalproject_python_userapp_202201.fragments.review.ReviewNotCompleteFragment
import com.neppplus.finalproject_python_userapp_202201.models.BasicResponse
import com.neppplus.finalproject_python_userapp_202201.models.ProductData
import com.neppplus.finalproject_python_userapp_202201.models.ProductOptionValueData
import com.neppplus.finalproject_python_userapp_202201.utils.WonFormatUtil
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ViewProductDetailActivity : BaseActivity() {

    lateinit var binding: ActivityViewProductDetailBinding

    lateinit var mProduct : ProductData

    val mFragmentList = ArrayList<Fragment>()

    var buyQuantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_product_detail)
        mProduct = intent.getSerializableExtra("product") as ProductData
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnCart.setOnClickListener {

            val selectedOptionsJSONArr = JSONArray()

            for (view in binding.optionListLayout.children) {
                val txtOptionName = view.findViewById<TextView>(R.id.txtOptionName)
                val optionsValuesListSpinner = view.findViewById<Spinner>(R.id.optionsValuesListSpinner)

                val selectedValue =  optionsValuesListSpinner.selectedItem as ProductOptionValueData
                Log.d("??????????????????", selectedValue.id.toString())

                val jsonObj = JSONObject()
                jsonObj.put("option_id", txtOptionName.tag.toString().toInt())
                jsonObj.put("selected_value_id", selectedValue.id)

                selectedOptionsJSONArr.put(jsonObj)
            }

            Log.d("??????????????? JSON Str", selectedOptionsJSONArr.toString())

            apiService.postRequestCart(
                mProduct.id,
                buyQuantity,
                selectedOptionsJSONArr.toString()
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                    if (response.isSuccessful) {

                        val customView = LayoutInflater.from(mContext).inflate(R.layout.fragment_cart_bottom_sheet, null)
                        val dialog = BottomSheetDialog(mContext)

                        val imgProductThumbnail = customView.findViewById<ImageView>(R.id.imgProductThumbnail)
                        val imgClose = customView.findViewById<ImageView>(R.id.imgClose)
                        val btnGoCart = customView.findViewById<TextView>(R.id.btnGoCart)

                        if (mProduct.product_main_images.isNotEmpty()) {
                            Glide.with(mContext).load(mProduct.product_main_images[0].image_url).into(imgProductThumbnail)
                        }

                        dialog.setContentView(customView)
                        dialog.show()

                        imgClose.setOnClickListener {
                            dialog.dismiss()
                        }

                        btnGoCart.setOnClickListener {
                            val myIntent = Intent(mContext, MainActivity::class.java)
                            myIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            myIntent.putExtra("startFragmentIndex", 2)
                            startActivity(myIntent)
                        }

                    }
                    else {
                        Toast.makeText(
                            mContext,
                            "???????????? ????????? ??????????????????. ??????????????? ??????????????????.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })

        }

        binding.btnPlus.setOnClickListener {
            if (buyQuantity < 5) {
                buyQuantity += 1
                setQuantityAndPriceTxt()   
            }
            else {
                Toast.makeText(mContext, "?????? 5???????????? ???????????????.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnMinus.setOnClickListener {
            if (buyQuantity > 1) {
                buyQuantity -= 1
                setQuantityAndPriceTxt()
            }
            else {
                Toast.makeText(mContext, "?????? 1??? ????????? ???????????? ?????????.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setQuantityAndPriceTxt() {

        binding.txtOriginalPrice.text = WonFormatUtil.getWonFormat(mProduct.original_price * buyQuantity)
        binding.txtOriginalPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG or binding.txtOriginalPrice.paintFlags
        binding.txtSalePrice.text = WonFormatUtil.getWonFormat(mProduct.sale_price * buyQuantity)
        val salePercent = (((mProduct.original_price - mProduct.sale_price).toDouble() / mProduct.original_price.toDouble()) * 100).toInt()

        binding.txtSalePercent.text = "${salePercent}%"

        binding.txtQuantity.text = buyQuantity.toString()
    }

    override fun setValues() {

        setTitle("?????? ????????????")

        binding.txtProductName.text = mProduct.name

        setProductDataToUI()


        mFragmentList.add(ProductDetailFragment(mProduct))
        mFragmentList.add(ProductReviewListFragment(mProduct))

        binding.productDetailViewPager.adapter = PagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(binding.productDetailTabLayout, binding.productDetailViewPager) { tab, position ->

            tab.text = when (position) {
                0 -> "?????? ??????"
                else -> "?????? ??????"
            }

        }.attach()

        getProductDetailFromServer()

    }

    private fun setProductDataToUI() {

        setQuantityAndPriceTxt()

        val imageAdapter = ImageSlideAdapter(mContext, mProduct.product_main_images)
        binding.productThumbnailViewPager.adapter = imageAdapter

        for (option in mProduct.product_options) {

            val row = LayoutInflater.from(mContext).inflate(R.layout.option_list_item, null)

            val txtOptionName = row.findViewById<TextView>(R.id.txtOptionName)
            val optionsValuesListSpinner = row.findViewById<Spinner>(R.id.optionsValuesListSpinner)

            txtOptionName.text = option.name
            txtOptionName.tag = option.id
            optionsValuesListSpinner.adapter = OptionValuesSpinnerAdapter(mContext, option.option_values)

            binding.optionListLayout.addView(row)

        }


        try {

            val reviewFrag = mFragmentList[1] as ProductReviewListFragment
            reviewFrag.mReviewList.clear()
            reviewFrag.mReviewList.addAll(mProduct.reviews)
            reviewFrag.mAdapter.notifyDataSetChanged()

        }
        catch (e: Exception) {
            e.printStackTrace()
        }

    }



    private fun getProductDetailFromServer() {
        apiService.getRequestProductDetail(
            mProduct.id
        ).enqueue(object :Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    mProduct = response.body()!!.data.product
                    setProductDataToUI()
                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })
    }


    private inner class PagerAdapter(fm: FragmentManager, lc: Lifecycle) :
        FragmentStateAdapter(fm, lc) {
        override fun getItemCount() = 2
        override fun createFragment(position: Int): Fragment {
            return mFragmentList[position]
        }
    }


}