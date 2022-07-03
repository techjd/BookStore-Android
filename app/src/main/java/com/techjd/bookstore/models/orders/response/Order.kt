package com.techjd.bookstore.models.orders.response

data class Order(
    val __v: Int,
    val _id: String,
    val address: String,
    val buyer: String,
    val createdAt: String,
    val isPaid: Boolean,
    val orderItems: List<OrderItem>,
    val seller: String,
    val status: String,
    val totalPrice: Int,
    val updatedAt: String,
    val paymentMode: String
)