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
import com.neppplus.finalproject_python_userapp_202201.models.BannerData
import com.neppplus.finalproject_python_userapp_202201.models.ProductData
import com.neppplus.finalproject_python_userapp_202201.utils.WonFormatUtil

class HomeRecyclerAdapter(
    val mContext: Context,
    val mBannerList:List<BannerData>,
    val mList:List<ProductData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClick {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClick {
        fun onItemLongClick(position: Int)
    }

    var oic : OnItemClick? = null
    var oilc : OnItemLongClick? = null


    inner class HeaderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind() {


        }
    }


    inner class TodayHotViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val imgProductThumbnail = view.findViewById<ImageView>(R.id.imgProductThumbnail)
        val imgLargeCategory = view.findViewById<ImageView>(R.id.imgLargeCategory)
        val txtSmallCategoryName = view.findViewById<TextView>(R.id.txtSmallCategoryName)
        val txtProductName = view.findViewById<TextView>(R.id.txtProductName)
        val txtSalePrice = view.findViewById<TextView>(R.id.txtSalePrice)


        fun bind(data: ProductData, position: Int) {

            Glide.with(mContext).load(data.product_main_images[0].image_url).into(imgProductThumbnail)
            Glide.with(mContext).load(data.large_category_info.icon_url).into(imgLargeCategory)

            txtSmallCategoryName.text = data.small_category_info.name
            txtSalePrice.text = WonFormatUtil.getWonFormat(data.sale_price)
            txtProductName.text = data.name


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


    val HEADER_VIEW_TYPE = 1000
    val TODAY_HOT_ITEM_TYPE = 1001

    override fun getItemViewType(position: Int): Int {

        return when (position) {

            0 -> HEADER_VIEW_TYPE
            else -> TODAY_HOT_ITEM_TYPE

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_VIEW_TYPE -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.main_banner_layout, parent, false)
                HeaderViewHolder(view)
            }
            else -> {

                val view = LayoutInflater.from(mContext).inflate(R.layout.today_hot_product_list_item, parent, false)
                TodayHotViewHolder(view)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                holder.bind()

            }
            is TodayHotViewHolder -> {

                holder.bind(mList[position - 1], position-1)

            }
        }
    }

    override fun getItemCount() = mList.size

}