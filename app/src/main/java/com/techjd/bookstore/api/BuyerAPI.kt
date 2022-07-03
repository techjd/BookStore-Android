package com.techjd.bookstore.api

import com.techjd.bookstore.models.address.Address
import com.techjd.bookstore.models.address.request.InputAddress
import com.techjd.bookstore.models.allOrders.AllOrder
import com.techjd.bookstore.models.books.Books
import com.techjd.bookstore.models.history.History
import com.techjd.bookstore.models.orders.request.OrdersRequest
import com.techjd.bookstore.models.orders.response.Orders
import com.techjd.bookstore.models.paymentstatus.PaymentStatusRequest
import com.techjd.bookstore.utils.Constants
import retrofit2.Response
import retrofit2.http.*

interface BuyerAPI {

    @GET(Constants.GET_ALL_BOOKS_ENDPOINT)
    suspend fun getAllBooks(): Response<Books>

    @GET(Constants.GET_USER_ADDRESS_ENDPOINT)
    suspend fun getAddress(@Header("Authorization") token: String): Response<Address>

    @POST(Constants.ADD_USER_ADDRESS_ENDPOINT)
    suspend fun addAddress(
        @Header("Authorization") token: String,
        @Body inputAddress: InputAddress
    ): Response<Address>

    @POST(Constants.PLACE_ORDER)
    suspend fun placeOrder(
        @Header("Authorization") token: String,
        @Body ordersRequest: OrdersRequest
    ): Response<Orders>

    @PATCH(Constants.UPDATE_PAYMENT_STATUS)
    suspend fun updatePaymentStatus(
        @Header("Authorization") token: String,
        @Body paymentStatusRequest: PaymentStatusRequest
    ): Response<Orders>

    @GET(Constants.GET_ORDERS)
    suspend fun getOrders(
        @Header("Authorization") token: String
    ): Response<AllOrder>

    @GET(Constants.GET_HISTORY)
    suspend fun getHistory(
        @Header("Authorization") token: String
    ): Response<AllOrder>
}