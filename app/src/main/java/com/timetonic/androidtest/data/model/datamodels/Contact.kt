package com.timetonic.androidtest.data.model.datamodels

import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("u_c")
    val uc: String,
    val lastName: String,
    val firstName: String,
    val sstamp: Long,
    val isConfirmed: Boolean
)
