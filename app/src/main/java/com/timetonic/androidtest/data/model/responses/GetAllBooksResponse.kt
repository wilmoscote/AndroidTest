package com.timetonic.androidtest.data.model.responses

import com.timetonic.androidtest.data.model.datamodels.AllBooks

data class GetAllBooksResponse(
    val status: String,
    val sstamp: Long,
    val allBooks: AllBooks,
)
