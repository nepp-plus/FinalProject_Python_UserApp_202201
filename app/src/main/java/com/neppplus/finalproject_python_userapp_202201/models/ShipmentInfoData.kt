package com.neppplus.finalproject_python_userapp_202201.models

import java.io.Serializable
import java.util.*

class ShipmentInfoData(

    val id: Int,
    val user_id: Int,
    val name: String,
    val phone: String,
    val email: String,
    val zipcode: String,
    val address1: String,
    val address2: String,
    val is_basic_address: Boolean,
    val memo: String,
    val created_at: Date,

): Serializable {
}