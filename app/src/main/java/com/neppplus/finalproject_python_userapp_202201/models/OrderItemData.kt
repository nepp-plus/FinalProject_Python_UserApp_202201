package com.neppplus.finalproject_python_userapp_202201.models

import java.io.Serializable

class OrderItemData(

    val id: Int,
    val merchant_uid: String,
    val product_id: Int,
    val product_quantity: Int,
    var product: ProductData,
    val review: ReviewData,
    val selected_options: List<OrderItemOptionData>

): Serializable {
}