package com.neppplus.finalproject_python_userapp_202201.models

import java.io.Serializable

class CartData(

    val id: Int,
    val product_id: Int,
    val user_id: Int,
    val quantity: Int,

): Serializable {
}