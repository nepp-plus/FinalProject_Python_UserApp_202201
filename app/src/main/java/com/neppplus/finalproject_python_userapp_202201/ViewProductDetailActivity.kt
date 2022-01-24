package com.neppplus.finalproject_python_userapp_202201

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivityViewProductDetailBinding
import com.neppplus.finalproject_python_userapp_202201.models.ProductData
import com.neppplus.finalproject_python_userapp_202201.utils.WonFormatUtil

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

        binding.btnPlus.setOnClickListener {
            buyQuantity += 1
            setQuantityAndPriceTxt()
        }

        binding.btnMinus.setOnClickListener {
            if (buyQuantity > 1) {
                buyQuantity -= 1
                setQuantityAndPriceTxt()
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

    }
}