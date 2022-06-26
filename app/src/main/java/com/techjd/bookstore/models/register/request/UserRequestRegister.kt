package com.techjd.bookstore.models.register.request

data class UserRequestRegister(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val role: String
)