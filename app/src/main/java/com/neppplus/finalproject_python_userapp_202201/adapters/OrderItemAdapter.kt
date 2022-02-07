package com.neppplus.finalproject_python_userapp_202201.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.finalproject_python_userapp_202201.R
import com.neppplus.finalproject_python_userapp_202201.models.LargeCategoryData
import com.neppplus.finalproject_python_userapp_202201.models.OrderItemData
import com.neppplus.finalproject_python_userapp_202201.models.OrderItemOptionData

class OrderItemAdapter(
    val mContext: Context,
    val mList:List<OrderItemData>) : RecyclerView.Adapter<OrderItemAdapter.ViewHolder>() {

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
        val optionListLayout = view.findViewById<LinearLayout>(R.id.optionListLayout)
        val txtShipmentDate = view.findViewById<TextView>(R.id.txtShipmentDate)
        val btnWriteReview = view.findViewById<AppCompatButton>(R.id.btnWriteReview)

        fun bind(data: OrderItemData, position: Int) {

            Glide.with(mContext).load(data.product.product_main_images[0].image_url).into(imgProductThumbnail)
            txtProductName.text = data.product.name

            if (data.selected_options.isEmpty()) {
                optionListLayout.visibility = View.GONE
            }
            else {
                optionListLayout.visibility = View.VISIBLE
            }

            optionListLayout.removeAllViews()
            for (option in data.selected_options) {
                optionListLayout.addView(makeRow(option))
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.order_item_recycler_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position], position)
    }

    override fun getItemCount() = mList.size


    private fun  makeRow(option: OrderItemOptionData) : View{

        val row = LayoutInflater.from(mContext).inflate(R.layout.selected_option_list_item, null)

        val txtSelectedOptionValue = row.findViewById<TextView>(R.id.txtSelectedOptionValue)

        txtSelectedOptionValue.text = "- ${option.option.name} : ${option.value.name}"

        return row

    }

}