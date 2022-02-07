package com.neppplus.finalproject_python_userapp_202201.fragments.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.neppplus.finalproject_python_userapp_202201.R
import com.neppplus.finalproject_python_userapp_202201.databinding.FragmentProductReviewListBinding
import com.neppplus.finalproject_python_userapp_202201.fragments.BaseFragment
import com.neppplus.finalproject_python_userapp_202201.models.ProductData

class ProductReviewListFragment(val mProduct: ProductData) : BaseFragment() {

    lateinit var binding: FragmentProductReviewListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_review_list, container, false)
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


    }

}