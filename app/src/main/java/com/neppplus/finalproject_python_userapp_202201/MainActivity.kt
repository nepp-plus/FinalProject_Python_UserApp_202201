package com.neppplus.finalproject_python_userapp_202201

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivityMainBinding
import com.neppplus.finalproject_python_userapp_202201.fragments.CartFragment
import com.neppplus.finalproject_python_userapp_202201.fragments.CategoryFragment
import com.neppplus.finalproject_python_userapp_202201.fragments.HomeFragment
import com.neppplus.finalproject_python_userapp_202201.fragments.MyInfoFragment

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        binding.mainViewPager.offscreenPageLimit = 4
        binding.mainViewPager.adapter = PagerAdapter(supportFragmentManager, lifecycle)
        binding.mainViewPager.registerOnPageChangeCallback(PageChangeCallback())
        binding.mainBottomNav.setOnItemSelectedListener { navigationSelected(it) }

    }

    private inner class PagerAdapter(fm: FragmentManager, lc: Lifecycle) :
        FragmentStateAdapter(fm, lc) {
        override fun getItemCount() = 4
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> HomeFragment()
                1 -> CategoryFragment()
                2 -> CartFragment()
                3 -> MyInfoFragment()
                else -> error("no such position: $position")
            }
        }
    }

    private fun navigationSelected(item: MenuItem): Boolean {
        val checked = item.setChecked(true)
        when (checked.itemId) {
            R.id.nav_home -> {
                binding.mainViewPager.currentItem = 0
                return true
            }
            R.id.nav_category -> {
                binding.mainViewPager.currentItem = 1
                return true
            }
            R.id.nav_cart -> {
                binding.mainViewPager.currentItem = 2
                return true
            }
            R.id.nav_my_info -> {
                binding.mainViewPager.currentItem = 3
                return true
            }
        }
        return false
    }

    private inner class PageChangeCallback: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.mainBottomNav.selectedItemId = when (position) {
                0 -> R.id.nav_home
                1 -> R.id.nav_category
                2 -> R.id.nav_cart
                3 -> R.id.nav_my_info
                else -> error("no such position: $position")
            }
        }
    }
}