package com.timetonic.androidtest.data.model.responses

import com.google.gson.annotations.SerializedName

data class OauthResponse(
    val status: String,
    val oauthkey: String?,
    val id: String?,
    @SerializedName("o_u")
    val ou: String?,
    val createdVNB: String,
    val req: String,
    val error: String?
)