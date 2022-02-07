package com.neppplus.finalproject_python_userapp_202201.api

import com.neppplus.finalproject_python_userapp_202201.models.BasicResponse
import com.neppplus.finalproject_python_userapp_202201.models.LargeCategoryData
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
        @Query("large_category_id") largeCategoryId: Int,
    ): Call<BasicResponse>


    @FormUrlEncoded
    @POST("/cart")
    fun postRequestCart(
        @Field("product_id") productId: Int,
        @Field("quantity") quantity: Int,
        @Field("option_info_str") optionStr: String,
    ): Call<BasicResponse>

    @GET("/cart")
    fun getRequestMyCart(
    ): Call<BasicResponse>

    @DELETE("/cart")
    fun deleteRequestCart(
        @Query("cart_id") cartId: Int,
    ): Call<BasicResponse>

    @GET("/shipmentinfo")
    fun getRequestShimentInfoList(
    ): Call<BasicResponse>

    @FormUrlEncoded
    @POST("/shipmentinfo")
    fun postRequestAddShipmentInfo(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("zipcode") zipcode: String,
        @Field("address1") address1: String,
        @Field("address2") address2: String,
        @Field("is_basic_address") is_basic_address: Boolean,
        @Field("memo") memo: String,

    ): Call<BasicResponse>


    @FormUrlEncoded
    @POST("/order")
    fun postRequestOrder(
        @Field("receiver_name") receiverName: String,
        @Field("address") address: String,
        @Field("number_zip") zipCode: String,
        @Field("phone_num") phoneNum: String,
        @Field("request_message") request_message: String,
        @Field("payment_uid") paymentUid: String,
        @Field("merchant_uid") merchantUid: String,
        @Field("item_list") itemListJsonStr: String,

        ): Call<BasicResponse>


    @GET("/todayshot")
    fun getRequestTodayHot(
    ): Call<BasicResponse>

    @GET("/main")
    fun getRequestMainBanner(
    ): Call<BasicResponse>


    @GET("/review")
    fun getRequestMyReview(
        @Query("type") type: String,
    ): Call<BasicResponse>

    @FormUrlEncoded
    @POST("/review")
    fun postRequestReview(
        @Field("order_item_id") orderItemId: Int,
        @Field("review_title") title: String,
        @Field("review_content") content: String,
        @Field("score") score: Float,

        ): Call<BasicResponse>

}