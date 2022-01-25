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

    @GET("/user")
    fun getRequestMyInfo(

    ): Call<BasicResponse>


    @GET("/largecategory/{large_category_id}/smallcategory")
    fun getRequestSmallCategory(
        @Path("large_category_id") id: Int,
    ): Call<BasicResponse>

    @GET("/smallcategory/{small_category_id}/product")
    fun getRequestProductsBySmallCategory(
        @Path("small_category_id") id: Int,
    ): Call<BasicResponse>


    @FormUrlEncoded
    @POST("/cart")
    fun postRequestCart(
        @Field("product_id") productId: Int,
        @Field("quantity") quantity: Int,
    ): Call<BasicResponse>

    @GET("/cart")
    fun getRequestMyCart(
    ): Call<BasicResponse>

    @DELETE("/cart")
    fun deleteRequestCart(
        @Query("product_id") productId: Int,
    ): Call<BasicResponse>

    @GET("/shipmentinfo")
    fun getRequestShimentInfoList(
    ): Call<BasicResponse>

}