package com.techjd.bookstore.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {

    private var prefs =
        context.getSharedPreferences(Constants.PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(Constants.USER_TOKEN, token)
        editor.apply()
    }

    fun saveSellerIdofCart(id: String) {
        val editor = prefs.edit()
        editor.putString(Constants.SELLER_ID, id)
        editor.apply()
    }

    fun getSellerIdOfCart(): String? {
        return prefs.getString(Constants.SELLER_ID, null)
    }

    fun getToken(): String? {
        return prefs.getString(Constants.USER_TOKEN, null)
    }

    fun getTokenWithBearer(): String? {
        return "Bearer ${prefs.getString(Constants.USER_TOKEN, null)}"
    }
}