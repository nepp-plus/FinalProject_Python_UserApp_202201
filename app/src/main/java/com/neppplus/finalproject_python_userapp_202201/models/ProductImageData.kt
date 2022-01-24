package com.neppplus.finalproject_python_userapp_202201.models

import java.io.Serializable

class ProductImageData(

    val id: Int,
    val product_id: Int,
    val index: Int,
    val image_url: String,

): Serializable {
}