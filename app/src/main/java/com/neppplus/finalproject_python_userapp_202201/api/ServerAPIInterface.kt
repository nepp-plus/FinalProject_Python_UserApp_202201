package com.neppplus.gudocin_android.api

import com.neppplus.gudocin_android.datas.BasicResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ServerAPIInterface {

//    회원가입 기능

    @FormUrlEncoded
    @PUT("/user")
    fun putRequestSignUp(
        @Field("email") email: String,
        @Field("password") pw: String,
        @Field("name") name: String,
        @Field("phone") phone: String,
    ): Call<BasicResponse>

}