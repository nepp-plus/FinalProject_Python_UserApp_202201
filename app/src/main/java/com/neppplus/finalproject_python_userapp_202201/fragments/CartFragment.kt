package com.neppplus.finalproject_python_userapp_202201.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.neppplus.finalproject_python_userapp_202201.PurchaseActivity
import com.neppplus.finalproject_python_userapp_202201.R
import com.neppplus.finalproject_python_userapp_202201.databinding.FragmentCartBinding
import com.neppplus.finalproject_python_userapp_202201.models.BasicResponse
import com.neppplus.finalproject_python_userapp_202201.models.CartData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat

class CartFragment : BaseFragment() {

    lateinit var binding: FragmentCartBinding
    val mCartList = ArrayList<CartData>()

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

        binding.selectAllCheckBox.setOnCheckedChangeListener { compoundButton, isChecked ->

            if (isChecked) {

                for (i in  0 until  binding.cartListLayout.childCount) {

                    val row = binding.cartListLayout.getChildAt(i)

                    val checkBox = row.findViewById<CheckBox>(R.id.productCheckBox)
                    checkBox.isChecked = true

                }

            }
            else {

                for (i in  0 until  binding.cartListLayout.childCount) {

                    val row = binding.cartListLayout.getChildAt(i)

                    val checkBox = row.findViewById<CheckBox>(R.id.productCheckBox)
                    checkBox.isChecked = false

                }
            }
            calculateTotalPrice()

        }
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
                    mCartList.clear()

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

                        mCartList.addAll(br.data.carts)

                        br.data.carts.forEach {

                            val row = makeCartRow(it)


                            binding.cartListLayout.addView(row)

                        }

                        binding.selectAllCheckBox.isChecked = true

                    }

                }


            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }

    private fun calculateTotalPrice() {

        var sum = 0
        var count = 0

        for (data in mCartList ) {
            if (data.isBuy) {
                sum += data.quantity * data.product_info.sale_price
                count++
            }

        }

        binding.txtProductPrice.text = NumberFormat.getNumberInstance().format(sum)

        var shipmentFee = 0

        if (sum < 30000) {
            shipmentFee = 3000
        }
        binding.txtShipmentFee.text = NumberFormat.getNumberInstance().format(shipmentFee)

        val totalPrice = sum+shipmentFee
        binding.txtTotalPrice.text =NumberFormat.getNumberInstance().format(totalPrice)

        if (count == 0) {
            binding.btnBuy.isEnabled = false
            binding.btnBuy.text = "구매할 상품을 선택해주세요."
        }
        else {
            binding.btnBuy.text = "구매하기 (${count}개)"
            binding.btnBuy.isEnabled = true
        }

    }

    fun makeCartRow(data: CartData) : View {
        val row = LayoutInflater.from(mContext).inflate(R.layout.cart_list_item, null)

        val productCheckBox = row.findViewById<CheckBox>(R.id.productCheckBox)
        val txtSalePrice = row.findViewById<TextView>(R.id.txtSalePrice)
        val txtItemTotalPrice = row.findViewById<TextView>(R.id.txtItemTotalPrice)
        val btnDelete = row.findViewById<Button>(R.id.btnDelete)
        val cartCountSpinner = row.findViewById<Spinner>(R.id.cartCountSpinner)

        productCheckBox.text = data.product_info.name
        txtSalePrice.text = NumberFormat.getNumberInstance().format(data.product_info.sale_price)

        cartCountSpinner.setSelection(data.quantity - 1)


        fun getItemTotalPrice() : Int {

            return if (productCheckBox.isChecked) {
                data.quantity = cartCountSpinner.selectedItemPosition+1
                data.quantity * data.product_info.sale_price
            }
            else {
                0
            }


        }

        productCheckBox.setOnCheckedChangeListener { compoundButton, isChecked ->

            data.isBuy = isChecked
//            if (!isChecked) {
//                binding.selectAllCheckBox.isChecked = false
//            }
            txtItemTotalPrice.text = NumberFormat.getNumberInstance().format(getItemTotalPrice())
            calculateTotalPrice()

        }

        cartCountSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                txtItemTotalPrice.text = NumberFormat.getNumberInstance().format(getItemTotalPrice())
                calculateTotalPrice()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }


        return row
    }

}