package com.neppplus.finalproject_python_userapp_202201.models

import com.google.gson.annotations.SerializedName

class AddressData(

    var oldAddress: String,
    var roadAddress: String,
    var zipCode: String,

) {

    constructor() : this("", "", "")

}