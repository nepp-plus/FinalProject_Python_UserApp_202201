package com.neppplus.finalproject_python_userapp_202201.models

import com.google.gson.annotations.SerializedName
import com.neppplus.finalproject_python_userapp_202201.models.LargeCategoryData
import com.neppplus.finalproject_python_userapp_202201.models.UserData

class DataResponse(
    val user: UserData,
    val token: String,

    val large_categories: List<LargeCategoryData>,
) {
}