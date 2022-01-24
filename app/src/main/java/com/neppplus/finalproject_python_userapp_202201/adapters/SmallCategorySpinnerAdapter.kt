package com.neppplus.finalproject_python_userapp_202201.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.neppplus.finalproject_python_userapp_202201.R
import com.neppplus.finalproject_python_userapp_202201.models.SmallCategoryData

class SmallCategorySpinnerAdapter(
    val mContext: Context,
    val mList: List<SmallCategoryData>
) : ArrayAdapter<SmallCategoryData>(mContext, R.layout.small_category_spinner_list_item, mList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row== null) {
            row = LayoutInflater.from(mContext).inflate(R.layout.small_category_spinner_list_item, null)
        }
        row!!

        val data = mList[position]

        val txtCategoryName = row.findViewById<TextView>(R.id.txtCategoryName)
        txtCategoryName.text = data.name

        return row
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

}