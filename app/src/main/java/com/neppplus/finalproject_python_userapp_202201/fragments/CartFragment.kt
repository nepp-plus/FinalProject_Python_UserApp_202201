package com.neppplus.finalproject_python_userapp_202201.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.neppplus.finalproject_python_userapp_202201.PurchaseActivity
import com.neppplus.finalproject_python_userapp_202201.R
import com.neppplus.finalproject_python_userapp_202201.databinding.FragmentCartBinding
import com.neppplus.finalproject_python_userapp_202201.databinding.FragmentHomeBinding
import com.neppplus.finalproject_python_userapp_202201.models.BasicResponse
import com.neppplus.finalproject_python_userapp_202201.models.CartData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartFragment : BaseFragment() {

    lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.btnBuy.setOnClickListener {

            val buyProductIds = arrayOf(1,2,3)

            val myIntent = Intent(mContext, PurchaseActivity::class.java)
            myIntent.putExtra("buyProductIds", buyProductIds)
            startActivity(myIntent)

        }
    }

    override fun setValues() {

        getMyCartListFromServer()
    }

    private fun getMyCartListFromServer() {

        apiService.getRequestMyCart().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    binding.cartListLayout.removeAllViews()

                    val br = response.body()!!

                    if (br.data.carts.isEmpty()) {
                        binding.emptyCartLayout.visibility = View.VISIBLE
                        binding.notEmptyLayout.visibility = View.GONE
                        binding.putchaseLayout.visibility = View.GONE
                    }
                    else {

                        binding.emptyCartLayout.visibility = View.GONE
                        binding.notEmptyLayout.visibility = View.VISIBLE
                        binding.putchaseLayout.visibility = View.VISIBLE

                        br.data.carts.forEach {

                            val row = makeCartRow(it)


                            binding.cartListLayout.addView(row)

                        }

                    }

                }


            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }

    fun makeCartRow(data: CartData) : View {
        val row = LayoutInflater.from(mContext).inflate(R.layout.cart_list_item, null)
        return row
    }

}