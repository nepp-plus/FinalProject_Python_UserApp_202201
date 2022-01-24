package com.neppplus.finalproject_python_userapp_202201

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.finalproject_python_userapp_202201.models.LargeCategoryData
import java.text.NumberFormat
import java.text.SimpleDateFormat

class LargeCategoryAdapter(
    val mContext: Context,
    val mList:List<LargeCategoryData>) : RecyclerView.Adapter<LargeCategoryAdapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgIcon = view.findViewById<ImageView>(R.id.imgIcon)
        val txtCategoryName = view.findViewById<TextView>(R.id.txtCategoryName)

        fun bind(data: LargeCategoryData) {

            Glide.with(mContext).load(data.icon_url).into(imgIcon)
            txtCategoryName.text = data.name

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.large_category_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}