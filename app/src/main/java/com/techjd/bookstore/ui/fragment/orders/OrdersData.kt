package com.techjd.bookstore.ui.fragment.orders

import com.techjd.bookstore.models.allOrders.OrderItem

data class OrdersData(
    val status: String,
    val sellerName: String,
    val orderItem: OrderItem,
    val paymentStatus: String
)
