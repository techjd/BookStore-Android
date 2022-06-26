package com.techjd.bookstore.api

import com.techjd.bookstore.models.books.Books
import com.techjd.bookstore.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface BuyerAPI {

    @GET(Constants.GET_ALL_BOOKS_ENDPOINT)
    suspend fun getAllBooks() : Response<Books>
}