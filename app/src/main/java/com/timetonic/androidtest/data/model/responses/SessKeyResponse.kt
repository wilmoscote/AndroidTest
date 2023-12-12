package com.timetonic.androidtest.data.model.responses

data class SessKeyResponse(
    val status: String,
    val sesskey: String?,
    val id: String?,
    val createdVNB: String,
    val req: String,
    val error: String?
)