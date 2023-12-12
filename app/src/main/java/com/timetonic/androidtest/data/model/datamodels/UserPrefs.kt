package com.timetonic.androidtest.data.model.datamodels

data class UserPrefs(
    val maxMsgsOffline: Int,
    val syncWithHubic: Boolean,
    val notificationEnabled: String,
    val uCoverLetOwnerDecide: Boolean,
    val uCoverColor: String,
    val uCoverUseLastImg: Boolean,
    val uCoverImg: String?,
    val uCoverType: String,
    val inGlobalSearch: Boolean,
    val inGlobalTasks: Boolean,
    val notifyEmailCopy: Boolean,
    val notifySmsCopy: Boolean,
    val notifyMobile: Boolean,
    val notifyWhenMsgInArchivedBook: Boolean
)