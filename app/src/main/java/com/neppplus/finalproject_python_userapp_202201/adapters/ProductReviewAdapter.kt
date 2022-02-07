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
import com.neppplus.finalproject_python_userapp_202201.models.ReviewData
import com.willy.ratingbar.BaseRatingBar
import java.text.SimpleDateFormat

class ProductReviewAdapter(
    val mContext: Context,
    val mList:List<ReviewData>) : RecyclerView.Adapter<ProductReviewAdapter.ViewHolder>() {

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

        private val reviewScoreRatingBar = view.findViewById<BaseRatingBar>(R.id.reviewScoreRatingBar)
        private val txtReviewTitle = view.findViewById<TextView>(R.id.txtReviewTitle)
        private val txtReviewContent = view.findViewById<TextView>(R.id.txtReviewContent)
        private var txtReviewDate = view.findViewById<TextView>(R.id.txtReviewDate)

        fun bind(data: ReviewData, position: Int) {

            reviewScoreRatingBar.rating = data.score.toFloat()
            txtReviewTitle.text = data.review_title
            txtReviewContent.text = data.review_content

            txtReviewDate.text = mDateFormat.format(data.created_at)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.product_review_list_item, parent, false)
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