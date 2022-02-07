package com.neppplus.finalproject_python_userapp_202201

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivityReviewListBinding
import com.neppplus.finalproject_python_userapp_202201.fragments.CartFragment
import com.neppplus.finalproject_python_userapp_202201.fragments.CategoryFragment
import com.neppplus.finalproject_python_userapp_202201.fragments.HomeFragment
import com.neppplus.finalproject_python_userapp_202201.fragments.MyInfoFragment
import com.neppplus.finalproject_python_userapp_202201.fragments.review.ReviewCompleteFragment
import com.neppplus.finalproject_python_userapp_202201.fragments.review.ReviewNotCompleteFragment

class ReviewListActivity : BaseActivity() {

    lateinit var binding : ActivityReviewListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_review_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        
    }

    override fun setValues() {
        
        setTitle("리뷰 관리")

        binding.reviewViewPager.adapter = PagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(binding.reviewTabLayout, binding.reviewViewPager) { tab, position ->

            tab.text = when (position) {
                0 -> "리뷰 작성"
                else -> "작성한 리뷰"
            }

        }.attach()
        
    }


    private inner class PagerAdapter(fm: FragmentManager, lc: Lifecycle) :
        FragmentStateAdapter(fm, lc) {
        override fun getItemCount() = 2
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ReviewNotCompleteFragment()
                1 -> ReviewCompleteFragment()
                else -> error("no such position: $position")
            }
        }
    }

}