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
import com.neppplus.finalproject_python_userapp_202201.models.ProductData

class ProductAdapter(
    val mContext: Context,
    val mList:List<ProductData>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    interface OnItemClick {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClick {
        fun onItemLongClick(position: Int)
    }

    var oic : OnItemClick? = null
    var oilc : OnItemLongClick? = null

    fun setOnItemClick(onItemClick: OnItemClick) {
        this.oic = onItemClick
    }

    fun setOnItemLongClick(onItemLongClick: OnItemLongClick) {
        this.oilc = onItemLongClick
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: ProductData, position: Int) {




            if (oic != null) {
                view.setOnClickListener {
                    oic!!.onItemClick(position)
                }
            }


            if (oilc != null) {
                view.setOnLongClickListener {
                    oilc!!.onItemLongClick(position)
                    return@setOnLongClickListener true
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.product_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position], position)
    }

    override fun getItemCount() = mList.size

}