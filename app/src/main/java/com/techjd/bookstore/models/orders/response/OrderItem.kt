package com.techjd.bookstore.models.orders.response

data class OrderItem(
    val _id: String,
    val book: String,
    val price: Int,
    val qty: Int
)