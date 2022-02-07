package com.neppplus.finalproject_python_userapp_202201.fragments.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.finalproject_python_userapp_202201.R
import com.neppplus.finalproject_python_userapp_202201.databinding.FragmentProductDetailBinding
import com.neppplus.finalproject_python_userapp_202201.fragments.BaseFragment
import com.neppplus.finalproject_python_userapp_202201.models.ProductData

class ProductDetailFragment(val mProduct: ProductData) : BaseFragment() {

    lateinit var binding: FragmentProductDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)
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

        for (detailImage in mProduct.product_detail_images) {

            val imgv = ImageView(mContext)
            imgv.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            imgv.scaleType = ImageView.ScaleType.FIT_XY
            imgv.adjustViewBounds = true
            Glide.with(mContext).load(detailImage.image_url).into(imgv)

            binding.productDetailImgLinearLayout.addView(imgv)

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