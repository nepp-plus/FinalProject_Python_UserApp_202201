package com.neppplus.finalproject_python_userapp_202201.models

import java.io.Serializable

class ProductData(

    val id: Int,
    val small_category_id: Int,
    val name: String,
    val original_price: Int,
    val sale_price: Int,
    val product_detail_images: List<ProductImageData>

): Serializable {
}