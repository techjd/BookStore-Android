package com.techjd.bookstore.models.history

data class Book(
    val __v: Int,
    val _id: String,
    val author: String,
    val categoryId: String,
    val createdAt: String,
    val description: String,
    val imageUrl: String,
    val price: Int,
    val sellerId: String,
    val title: String,
    val updatedAt: String
)