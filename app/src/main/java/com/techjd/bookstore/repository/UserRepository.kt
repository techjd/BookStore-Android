package com.techjd.bookstore.repository

import com.techjd.bookstore.api.UserAPI
import com.techjd.bookstore.models.login.request.UserRequestLogin
import com.techjd.bookstore.models.register.request.UserRequestRegister
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI: UserAPI) {

    suspend fun signIn(userRequestLogin: UserRequestLogin) = userAPI.signIn(userRequestLogin)

    suspend fun signUp(userRequestRegister: UserRequestRegister) =
        userAPI.signUp(userRequestRegister)
}