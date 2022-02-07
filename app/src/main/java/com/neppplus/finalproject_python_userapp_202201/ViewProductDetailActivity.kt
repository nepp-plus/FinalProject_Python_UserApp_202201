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
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.neppplus.finalproject_python_userapp_202201.adapters.ImageSlideAdapter
import com.neppplus.finalproject_python_userapp_202201.adapters.OptionValuesSpinnerAdapter
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivityViewProductDetailBinding
import com.neppplus.finalproject_python_userapp_202201.models.BasicResponse
import com.neppplus.finalproject_python_userapp_202201.models.ProductData
import com.neppplus.finalproject_python_userapp_202201.models.ProductOptionValueData
import com.neppplus.finalproject_python_userapp_202201.utils.WonFormatUtil
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewProductDetailActivity : BaseActivity() {

    lateinit var binding: ActivityViewProductDetailBinding

    lateinit var mProduct : ProductData

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
                Log.d("선택한옵션값", selectedValue.id.toString())

                val jsonObj = JSONObject()
                jsonObj.put("option_id", txtOptionName.tag.toString().toInt())
                jsonObj.put("selected_value_id", selectedValue.id)

                selectedOptionsJSONArr.put(jsonObj)
            }

            Log.d("앱에서만든 JSON Str", selectedOptionsJSONArr.toString())

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
                            "장바구니 등록에 실패했습니다. 관리자에게 문의해주세요.",
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
                Toast.makeText(mContext, "최대 5개까지만 가능합니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnMinus.setOnClickListener {
            if (buyQuantity > 1) {
                buyQuantity -= 1
                setQuantityAndPriceTxt()
            }
            else {
                Toast.makeText(mContext, "최소 1개 이상은 구매해야 합니다.", Toast.LENGTH_SHORT).show()
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

        setTitle("상품 상세정보")

        binding.txtProductName.text = mProduct.name

        setQuantityAndPriceTxt()

        val imageAdapter = ImageSlideAdapter(mContext, mProduct.product_main_images)
        binding.productThumbnailViewPager.adapter = imageAdapter

        for (detailImage in mProduct.product_detail_images) {

            val imgv = ImageView(mContext)
            imgv.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            imgv.scaleType = ImageView.ScaleType.FIT_XY
            imgv.adjustViewBounds = true
            Glide.with(mContext).load(detailImage.image_url).into(imgv)

            binding.productDetailImgLinearLayout.addView(imgv)

        }

        for (option in mProduct.product_options) {

            val row = LayoutInflater.from(mContext).inflate(R.layout.option_list_item, null)

            val txtOptionName = row.findViewById<TextView>(R.id.txtOptionName)
            val optionsValuesListSpinner = row.findViewById<Spinner>(R.id.optionsValuesListSpinner)

            txtOptionName.text = option.name
            txtOptionName.tag = option.id
            optionsValuesListSpinner.adapter = OptionValuesSpinnerAdapter(mContext, option.option_values)

            binding.optionListLayout.addView(row)

        }

        for (info in mProduct.product_infos) {
            val row = LayoutInflater.from(mContext).inflate(R.layout.product_info_list_item, null)

            val txtDescription = row.findViewById<TextView>(R.id.txtDescription)
            val txtDescriptionContent = row.findViewById<TextView>(R.id.txtDescriptionContent)

            txtDescription.text = info.description
            txtDescriptionContent.text = info.description_content

            binding.productInfoLayout.addView(row)

        }

    }
}