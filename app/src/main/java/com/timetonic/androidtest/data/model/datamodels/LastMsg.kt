package com.timetonic.androidtest.data.model.datamodels

import com.google.gson.annotations.SerializedName

data class LastMsg(
    val smid: Int,
    val uuid: String,
    val sstamp: Long,
    val lastCommentId: Int,
    val msgBody: String,
    val msgType: String,
    val msgMethod: String,
    val msgColor: String,
    val nbComments: Int,
    val pid: Int,
    val nbMedias: Int,
    val nbEmailCids: Int,
    val nbDocs: Int,
    @SerializedName("b_c")
    val bc: String,
    @SerializedName("b_o")
    val bo: String,
    @SerializedName("u_c")
    val uc: String,
    val linkedRowId: String?,
    val linkedTabId: String?,
    val linkMessage: String,
    val linkedFieldId: String?,
    val msg: String,
    val del: Boolean,
    val created: Long,
    val lastModified: Long,
    val docs: List<Doc>
)
