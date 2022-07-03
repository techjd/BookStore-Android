package com.techjd.bookstore.models.allOrders

data class AllOrder(
    val `data`: List<Data>,
    val message: String,
    val status: String
)