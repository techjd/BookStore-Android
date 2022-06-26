package com.techjd.bookstore.models.books

data class Data(
    val __v: Int,
    val _id: String,
    val author: String,
    val categoryId: CategoryId,
    val createdAt: String,
    val description: String,
    val imageUrl: String,
    val price: Int,
    val sellerId: SellerId,
    val title: String,
    val updatedAt: String
)