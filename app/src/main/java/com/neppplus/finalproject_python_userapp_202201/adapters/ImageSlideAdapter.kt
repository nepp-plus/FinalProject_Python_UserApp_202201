package com.neppplus.finalproject_python_userapp_202201.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.finalproject_python_userapp_202201.R
import com.neppplus.finalproject_python_userapp_202201.models.LargeCategoryData
import com.neppplus.finalproject_python_userapp_202201.models.ImageData

class ImageSlideAdapter(
    val mContext: Context,
    val mList:List<ImageData>) : RecyclerView.Adapter<ImageSlideAdapter.ViewHolder>() {


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val imgImage = view.findViewById<ImageView>(R.id.imgImage)

        fun bind(data: ImageData, position: Int) {

            Glide.with(mContext).load(data.image_url).into(imgImage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.image_slider_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position], position)
    }

    override fun getItemCount() = mList.size

}