package com.neppplus.finalproject_python_userapp_202201

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ReviewListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        
    }

    override fun setValues() {
        
        setTitle("리뷰 관리")
        
    }
}