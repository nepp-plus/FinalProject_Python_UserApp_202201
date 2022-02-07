package com.neppplus.finalproject_python_userapp_202201.models

import java.io.Serializable

class OrderItemOptionData(

    val id: Int,
    val order_id: Int,
    val order_item_id: Int,
    val option_id: Int,
    val value_id: Int,
    val option: ProductOptionData,
    val value: ProductOptionValueData,
): Serializable {
}