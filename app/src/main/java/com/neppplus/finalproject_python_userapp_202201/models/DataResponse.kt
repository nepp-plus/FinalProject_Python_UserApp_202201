package com.neppplus.finalproject_python_userapp_202201.models

import com.google.gson.annotations.SerializedName
import com.neppplus.finalproject_python_userapp_202201.models.LargeCategoryData
import com.neppplus.finalproject_python_userapp_202201.models.UserData

class DataResponse(
    val user: UserData,
    val token: String,

    val large_categories: List<LargeCategoryData>,
    val small_categories: List<SmallCategoryData>,

    val products: List<ProductData>,
    val carts: List<CartData>,


    val user_all_address: List<ShipmentInfoData>,

) {
}