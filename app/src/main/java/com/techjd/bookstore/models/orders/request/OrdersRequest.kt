package com.techjd.bookstore.models.orders.request

data class OrdersRequest(
    val address: String,
    val orderItems: List<OrderItem>,
    val seller: String,
    val totalPrice: Int,
    val paymentMode: String
)