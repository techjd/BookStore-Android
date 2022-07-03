package com.techjd.bookstore.models.orders.request

data class OrderItem(
    val book: String,
    val price: Int,
    val qty: Int
)