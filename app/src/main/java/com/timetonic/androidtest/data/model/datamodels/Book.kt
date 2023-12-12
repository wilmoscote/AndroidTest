package com.timetonic.androidtest.data.model.datamodels

import com.google.gson.annotations.SerializedName

data class Book(
    val invited: Boolean,
    val accepted: Boolean,
    val archived: Boolean,
    val notificationWeb: String,
    val notificationAudio: String,
    val showFpOnOpen: Boolean,
    val sstamp: Long,
    val del: Boolean,
    val hideMessage: String,
    val hideBookMembers: String,
    val description: String?,
    val defaultTemplate: String,
    val isDownloadable: Boolean,
    val canDisableSync: Boolean,
    @SerializedName("b_c")
    val bc: String,
    @SerializedName("b_o")
    val bo: String,
    val cluster: String,
    val tags: String?,
    val langs: String?,
    @SerializedName("contact_u_c")
    val contactUc: String?,
    val nbNotRead: Int,
    val nbMembers: Int,
    val members: List<Member>,
    val fpForm: FpForm,
    val lastMsg: LastMsg,
    val nbMsgs: Int,
    val userPrefs: UserPrefs,
    val ownerPrefs: OwnerPrefs,
    val sbid: Int,
    val lastMsgRead: Int,
    val lastMedia: Int,
    val favorite: Boolean,
    val order: Int
)
