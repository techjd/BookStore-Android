package com.techjd.bookstore.repository

import androidx.lifecycle.LiveData
import androidx.room.PrimaryKey
import com.techjd.bookstore.api.BuyerAPI
import com.techjd.bookstore.api.UserAPI
import com.techjd.bookstore.db.dao.CartDao
import com.techjd.bookstore.db.models.Data
import com.techjd.bookstore.models.books.Books
import javax.inject.Inject

class BuyerRepository @Inject constructor(
    private val buyerAPI: BuyerAPI,
    private val cartDao: CartDao
) {

    suspend fun getAllBooks() = buyerAPI.getAllBooks()

    suspend fun insertBooks(book: Data) = cartDao.insertBook(book)

    suspend fun updateBook(book: Data) = cartDao.updateBook(book)

    suspend fun deleteBook(book: Data) = cartDao.deleteBook(book)

    suspend fun getAddress(token: String) = buyerAPI.getAddress(token)

    val getAllBooksOfCard = cartDao.getAllBooks()

    suspend fun checkIfBookExists(id: String) = cartDao.checkIfItemExists(id)

    val getTotal: LiveData<Int> = cartDao.getTotal()

    val getTotalCountInCart: LiveData<Int> = cartDao.getCountInCart()
}