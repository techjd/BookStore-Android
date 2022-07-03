package com.techjd.bookstore.models.history

data class OrderItem(
    val _id: String,
    val book: Book,
    val price: Int,
    val qty: Int
)