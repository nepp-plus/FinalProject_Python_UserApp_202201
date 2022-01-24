package com.neppplus.finalproject_python_userapp_202201.api

import com.neppplus.finalproject_python_userapp_202201.models.BasicResponse
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


    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin(
        @Field("email") email: String,
        @Field("password") pw: String,
    ): Call<BasicResponse>


    @GET("/largecategory")
    fun getRequestLargeCategory(

    ): Call<BasicResponse>

}