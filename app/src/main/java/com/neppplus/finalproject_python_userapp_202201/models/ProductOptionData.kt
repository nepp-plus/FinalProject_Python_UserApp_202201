package com.neppplus.finalproject_python_userapp_202201.models

import java.io.Serializable

class ProductOptionData(

    val id: Int,
    val name: String,
    val product_id: Int,
    val option_values: List<ProductOptionValueData>

): Serializable {
}