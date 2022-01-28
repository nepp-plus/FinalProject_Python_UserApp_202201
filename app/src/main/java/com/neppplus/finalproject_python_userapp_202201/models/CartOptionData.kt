package com.neppplus.finalproject_python_userapp_202201.models

import java.io.Serializable

class CartOptionData(

    val id: Int,
    val user_id: Int,
    val cart_id: Int,
    val option_id: Int,
    val value_id: Int,
    val option: ProductOptionData,
    val value: ProductOptionValueData,

): Serializable {
}