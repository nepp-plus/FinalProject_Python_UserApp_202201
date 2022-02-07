package com.neppplus.finalproject_python_userapp_202201

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivityEditReviewBinding
import com.neppplus.finalproject_python_userapp_202201.models.BasicResponse
import com.neppplus.finalproject_python_userapp_202201.models.OrderItemData
import com.neppplus.finalproject_python_userapp_202201.models.OrderItemOptionData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditReviewActivity : BaseActivity() {

    lateinit var binding: ActivityEditReviewBinding
    lateinit var mOrderItemData: OrderItemData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_review)
        mOrderItemData = intent.getSerializableExtra("orderItem") as OrderItemData
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnCancel.setOnClickListener {

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("리뷰 작성 취소")
            alert.setMessage("정말 작성을 취소하겠습니까? 작성한 내용은 저장되지 않습니다.")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->

                finish()

            })
            alert.setNegativeButton("취소", null)
            alert.show()

        }

        binding.btnSaveReview.setOnClickListener {

            val score = binding.reviewScoreRatingBar.rating
            if (score == 0f) {
                Toast.makeText(mContext, "평점을 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inputContent = binding.edtReview.text.toString()

            if (inputContent.length < 5) {
                Toast.makeText(mContext, "리뷰는 최소 5자 이상 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inputTitle = binding.edtTitle.text.toString()

            if (inputTitle.length < 3) {
                Toast.makeText(mContext, "제목은 최소 3자 이상 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("리뷰 등록")
            alert.setMessage("리뷰를 등록하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->

                apiService.postRequestReview(
                    mOrderItemData.id,
                    inputTitle,
                    inputContent,
                    score,
                ).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {

                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }

                })

            })
            alert.setNegativeButton("취소", null)
            alert.show()

        }

    }

    override fun setValues() {
        setTitle("리뷰 작성하기")
        binding.txtProductName.text = mOrderItemData.product.name
        Glide.with(mContext).load(mOrderItemData.product.product_main_images[0].image_url).into(binding.imgProductThumbnail)

        if (mOrderItemData.selected_options.isNotEmpty()) {
            binding.optionListLayout.visibility = View.VISIBLE
        }
        for (option in mOrderItemData.selected_options) {
            binding.optionListLayout.addView(makeRow(option))
        }
    }


    private fun  makeRow(option: OrderItemOptionData) : View {

        val row = LayoutInflater.from(mContext).inflate(R.layout.selected_option_list_item, null)

        val txtSelectedOptionValue = row.findViewById<TextView>(R.id.txtSelectedOptionValue)

        txtSelectedOptionValue.text = "- ${option.option.name} : ${option.value.name}"

        return row

    }

}