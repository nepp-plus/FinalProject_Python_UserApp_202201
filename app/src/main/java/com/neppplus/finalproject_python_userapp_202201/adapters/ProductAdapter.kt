package com.neppplus.finalproject_python_userapp_202201.adapters

import android.content.Context
import android.graphics.Paint
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
import com.neppplus.finalproject_python_userapp_202201.utils.WonFormatUtil

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

        val imgProductThumbnail = view.findViewById<ImageView>(R.id.imgProductThumbnail)
        val txtProductName = view.findViewById<TextView>(R.id.txtProductName)
        val txtSalePercent = view.findViewById<TextView>(R.id.txtSalePercent)
        val txtOriginalPrice = view.findViewById<TextView>(R.id.txtOriginalPrice)
        val txtSalePrice = view.findViewById<TextView>(R.id.txtSalePrice)

        fun bind(data: ProductData, position: Int) {

            txtProductName.text = data.name
            txtOriginalPrice.text = WonFormatUtil.getWonFormat(data.original_price)
            txtOriginalPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG or txtOriginalPrice.paintFlags
            txtSalePrice.text = WonFormatUtil.getWonFormat(data.sale_price)

            val salePercent = (((data.original_price - data.sale_price).toDouble() / data.original_price.toDouble()) * 100).toInt()

            txtSalePercent.text = "${salePercent}%"

            if (data.product_main_images.isNotEmpty()) {
                Glide.with(mContext).load(data.product_main_images[0].image_url).into(imgProductThumbnail)
            }

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