package com.techjd.bookstore.api

import com.techjd.bookstore.models.login.request.UserRequestLogin
import com.techjd.bookstore.models.login.response.UserResponseLogin
import com.techjd.bookstore.models.register.request.UserRequestRegister
import com.techjd.bookstore.models.register.response.UserResponseRegister
import com.techjd.bookstore.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {

    @POST(Constants.REGISTER_ENDPOINT)
    suspend fun signUp(@Body userRequestRegister: UserRequestRegister): Response<UserResponseRegister>

    @POST(Constants.LOGIN_ENDPOINT)
    suspend fun signIn(@Body userRequestLogin: UserRequestLogin): Response<UserResponseLogin>
}