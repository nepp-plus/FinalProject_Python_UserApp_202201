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
import com.neppplus.finalproject_python_userapp_202201.models.OrderItemData
import com.neppplus.finalproject_python_userapp_202201.models.OrderItemOptionData
import com.willy.ratingbar.BaseRatingBar
import java.text.SimpleDateFormat

class OrderItemReviewAdapter(
    val mContext: Context,
    val mList:List<OrderItemData>) : RecyclerView.Adapter<OrderItemReviewAdapter.ViewHolder>() {

    val mDateFormat = SimpleDateFormat("yyyy.MM.dd")

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

        private val imgProductThumbnail = view.findViewById<ImageView>(R.id.imgProductThumbnail)
        private val txtProductName = view.findViewById<TextView>(R.id.txtProductName)
        private val reviewScoreRatingBar = view.findViewById<BaseRatingBar>(R.id.reviewScoreRatingBar)
        private val txtReviewTitle = view.findViewById<TextView>(R.id.txtReviewTitle)
        private val txtReviewContent = view.findViewById<TextView>(R.id.txtReviewContent)
        private var txtReviewDate = view.findViewById<TextView>(R.id.txtReviewDate)

        fun bind(data: OrderItemData, position: Int) {

            Glide.with(mContext).load(data.product.product_main_images[0].image_url).into(imgProductThumbnail)
            txtProductName.text = data.product.name

            reviewScoreRatingBar.rating = data.review.score.toFloat()
            txtReviewTitle.text = data.review.review_title
            txtReviewContent.text = data.review.review_content

            txtReviewDate.text = mDateFormat.format(data.review.created_at)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.order_item_review_list_item, parent, false)
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