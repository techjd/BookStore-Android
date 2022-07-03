package com.techjd.bookstore.models.history

data class History(
    val `data`: List<Data>,
    val message: String,
    val status: String
)