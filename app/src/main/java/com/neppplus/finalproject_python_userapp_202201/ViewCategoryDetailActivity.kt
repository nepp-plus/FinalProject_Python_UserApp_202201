package com.neppplus.finalproject_python_userapp_202201

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neppplus.finalproject_python_userapp_202201.models.LargeCategoryData

class ViewCategoryDetailActivity : BaseActivity() {

    lateinit var mLargeCategoryData: LargeCategoryData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_category_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mLargeCategoryData = intent.getSerializableExtra("category") as LargeCategoryData

        setTitle(mLargeCategoryData.name)

    }
}