package com.timetonic.androidtest.data.model.datamodels

data class Doc(
    val id: Int,
    val ext: String,
    val originName: String,
    val internName: String,
    val uuid: String,
    val size: Int,
    val type: String,
    val del: Boolean
)
