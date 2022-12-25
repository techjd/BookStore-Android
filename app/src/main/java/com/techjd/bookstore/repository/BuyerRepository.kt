package com.techjd.bookstore.repository

import androidx.lifecycle.LiveData
import androidx.room.PrimaryKey
import com.techjd.bookstore.api.BuyerAPI
import com.techjd.bookstore.api.UserAPI
import com.techjd.bookstore.db.dao.CartDao
import com.techjd.bookstore.db.models.Data
import com.techjd.bookstore.models.address.request.InputAddress
import com.techjd.bookstore.models.books.Books
import com.techjd.bookstore.models.orders.request.OrdersRequest
import com.techjd.bookstore.models.paymentstatus.PaymentStatusRequest
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

    suspend fun addAddress(token: String, inputAddress: InputAddress) =
        buyerAPI.addAddress(token, inputAddress)

    suspend fun placeOrder(token: String, ordersRequest: OrdersRequest) =
        buyerAPI.placeOrder(token, ordersRequest)

    suspend fun updatePaymentStatus(token: String, paymentStatusRequest: PaymentStatusRequest) =
        buyerAPI.updatePaymentStatus(token, paymentStatusRequest)

    suspend fun getOrders(token: String) =
        buyerAPI.getOrders(token)

    suspend fun getHistory(token: String) =
        buyerAPI.getHistory(token)

    val getAllBooksOfCard = cartDao.getAllBooks()

    suspend fun checkIfBookExists(id: String) = cartDao.checkIfItemExists(id)

    val getTotal: LiveData<Int> = cartDao.getTotal()

    val getTotalCountInCart: LiveData<Int> = cartDao.getCountInCart()

    suspend fun getCartCount() =
        cartDao.getCartCount()

    suspend fun checkIfUserIsInsertingBooksWithSameSeller() =
        cartDao.checkIfUserIsInsertingBooksWithSameSeller()

    suspend fun deleteAllBooks() = cartDao.deleteAll()
}