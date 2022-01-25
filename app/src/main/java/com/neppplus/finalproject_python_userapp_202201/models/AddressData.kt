package com.neppplus.finalproject_python_userapp_202201.models

import com.google.gson.annotations.SerializedName

class AddressData(

    var id: Int,
    @SerializedName("name")
    var placeName: String,
    var latitude: Double,
    var longitude: Double,

) {
}