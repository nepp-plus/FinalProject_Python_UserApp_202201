package com.neppplus.finalproject_python_userapp_202201.models

import java.io.Serializable
import java.util.*

class ReviewData(

    val id: Int,
    val user_id: Int,
    val review_title: String,
    val review_content: String,
    val score: Double,
    val writer: UserData,
    val created_at: Date,

): Serializable {
}