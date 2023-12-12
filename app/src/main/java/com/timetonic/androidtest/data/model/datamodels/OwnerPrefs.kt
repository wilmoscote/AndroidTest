package com.timetonic.androidtest.data.model.datamodels

data class OwnerPrefs(
    val fpAutoExport: Boolean,
    val oCoverColor: String,
    val oCoverUseLastImg: Boolean,
    val oCoverImg: String?,
    val oCoverType: String,
    val authorizeMemberBroadcast: Boolean,
    val acceptExternalMsg: Boolean,
    val title: String,
    val notifyMobileConfidential: Boolean
)
