package com.neppplus.finalproject_python_userapp_202201.models

class BasicResponse(
    var code: Int,
    var message: String,
    var data: DataResponse,
) {
}