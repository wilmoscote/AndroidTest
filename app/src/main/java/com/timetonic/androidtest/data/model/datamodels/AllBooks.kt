package com.timetonic.androidtest.data.model.datamodels

data class AllBooks(
    val nbBooks: Int,
    val nbContacts: Int,
    val contacts: List<Contact>,
    val books: List<Book>
)
