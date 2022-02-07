package com.neppplus.finalproject_python_userapp_202201

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivityEditReviewBinding
import com.neppplus.finalproject_python_userapp_202201.models.OrderItemData
import com.neppplus.finalproject_python_userapp_202201.models.OrderItemOptionData

class EditReviewActivity : BaseActivity() {

    lateinit var binding: ActivityEditReviewBinding
    lateinit var mOrderItemData: OrderItemData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_review)
        mOrderItemData = intent.getSerializableExtra("orderItem") as OrderItemData
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        setTitle("리뷰 작성하기")
        binding.txtProductName.text = mOrderItemData.product.name
        Glide.with(mContext).load(mOrderItemData.product.product_main_images[0].image_url).into(binding.imgProductThumbnail)

        if (mOrderItemData.selected_options.isNotEmpty()) {
            binding.optionListLayout.visibility = View.VISIBLE
        }
        for (option in mOrderItemData.selected_options) {
            binding.optionListLayout.addView(makeRow(option))
        }
    }


    private fun  makeRow(option: OrderItemOptionData) : View {

        val row = LayoutInflater.from(mContext).inflate(R.layout.selected_option_list_item, null)

        val txtSelectedOptionValue = row.findViewById<TextView>(R.id.txtSelectedOptionValue)

        txtSelectedOptionValue.text = "- ${option.option.name} : ${option.value.name}"

        return row

    }

}