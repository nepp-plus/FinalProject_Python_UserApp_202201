package com.neppplus.finalproject_python_userapp_202201.models

import java.io.Serializable
import java.util.*

class UserData(
    val id: Int,
    val email: String,
    val name: String,
    val phone: String,
    val created_at: Date,
    val retired_at: Date?
) : Serializable {
}