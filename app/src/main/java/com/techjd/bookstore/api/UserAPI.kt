package com.techjd.bookstore.api

import com.techjd.bookstore.models.login.request.UserRequestLogin
import com.techjd.bookstore.models.login.response.UserResponseLogin
import com.techjd.bookstore.models.register.request.UserRequestRegister
import com.techjd.bookstore.models.register.response.UserResponseRegister
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {

    @POST("auth/register")
    suspend fun signUp(@Body userRequestRegister: UserRequestRegister): Response<UserResponseRegister>

    @POST("auth/login")
    suspend fun signIn(@Body userRequestLogin: UserRequestLogin): Response<UserResponseLogin>
}