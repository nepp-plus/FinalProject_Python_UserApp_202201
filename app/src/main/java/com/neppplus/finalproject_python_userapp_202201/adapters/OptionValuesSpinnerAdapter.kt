package com.neppplus.finalproject_python_userapp_202201.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.neppplus.finalproject_python_userapp_202201.R
import com.neppplus.finalproject_python_userapp_202201.models.ProductOptionData
import com.neppplus.finalproject_python_userapp_202201.models.ProductOptionValueData
import com.neppplus.finalproject_python_userapp_202201.models.SmallCategoryData

class OptionValuesSpinnerAdapter(
    val mContext: Context,
    val mList: List<ProductOptionValueData>
) : ArrayAdapter<ProductOptionValueData>(mContext, R.layout.option_spinner_list_item, mList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row== null) {
            row = LayoutInflater.from(mContext).inflate(R.layout.option_spinner_list_item, null)
        }
        row!!

        val data = mList[position]

        val txtValueName = row.findViewById<TextView>(R.id.txtValueName)
        txtValueName.text = data.name

        return row
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

}