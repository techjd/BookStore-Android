package com.techjd.bookstore.models.history

data class Data(
    val __v: Int,
    val _id: String,
    val address: String,
    val buyer: String,
    val createdAt: String,
    val isPaid: Boolean,
    val orderItems: List<OrderItem>,
    val paymentMode: String,
    val seller: Seller,
    val status: String,
    val totalPrice: Int,
    val updatedAt: String
)