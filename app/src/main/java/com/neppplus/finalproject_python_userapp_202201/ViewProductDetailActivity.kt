package com.neppplus.finalproject_python_userapp_202201

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivityViewProductDetailBinding
import com.neppplus.finalproject_python_userapp_202201.models.ProductData

class ViewProductDetailActivity : BaseActivity() {

    lateinit var binding: ActivityViewProductDetailBinding

    lateinit var mProduct : ProductData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_product_detail)
        mProduct = intent.getSerializableExtra("product") as ProductData
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        setTitle("상품 상세정보")

        binding.txtProductName.text = mProduct.name

    }
}