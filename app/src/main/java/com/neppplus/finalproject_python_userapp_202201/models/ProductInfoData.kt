package com.neppplus.finalproject_python_userapp_202201.models

import java.io.Serializable

class ProductInfoData(

    val id: Int,
    val product_id: Int,
    val description: String,
    val description_content: String,

): Serializable {
}