package com.techjd.bookstore.models.register.response

data class User(
    val __v: Int,
    val _id: String,
    val address: String,
    val createdAt: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val role: String,
    val updatedAt: String
)