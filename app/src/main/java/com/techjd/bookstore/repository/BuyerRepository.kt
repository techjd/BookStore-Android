package com.techjd.bookstore.repository

import com.techjd.bookstore.api.BuyerAPI
import com.techjd.bookstore.api.UserAPI
import javax.inject.Inject

class BuyerRepository @Inject constructor(private val buyerAPI: BuyerAPI) {

    suspend fun getAllBooks() = buyerAPI.getAllBooks()
}