package com.techjd.bookstore.utils

object Constants {

    const val DEVELOPMENT_BASE_URL = "http://172.20.10.12:6000/api/" // PORT_NO = 6000
    const val PRODUCTION_BASE_URL = "" // Replace with Production URL
    const val PREFS_TOKEN_FILE = "PREFS_TOKEN_FILE"
    const val USER_TOKEN = "USER_TOKEN"
    const val REGISTER_ENDPOINT = "auth/register"
    const val LOGIN_ENDPOINT = "auth/login"
    const val GET_ALL_BOOKS_ENDPOINT = "buyer/books"
    const val GET_USER_ADDRESS_ENDPOINT = "buyer/getAddress"
    const val ADD_USER_ADDRESS_ENDPOINT = "buyer/addAddress"
    const val UPDATE_PAYMENT_STATUS = "buyer/updatePaymentStatus"
    const val PLACE_ORDER = "buyer/placeOrder"
    const val GET_ORDERS = "buyer/getAllOrders"
    const val GET_HISTORY = "buyer/getHistory"
    const val DATABASE_NAME = "bookstore"
    const val SELLER_ID = "SELLER_ID"
}