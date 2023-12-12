package com.timetonic.androidtest.data.model.datamodels

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("u_c")
    val uc: String,
    val invite: String,
    val right: Int,
    val access: Int,
    val hideMessage: String,
    val hideBookMembers: String,
    val apiRight: String
)
