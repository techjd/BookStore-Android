package com.techjd.bookstore.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.techjd.bookstore.models.failure.Failure
import com.techjd.bookstore.models.login.request.UserRequestLogin
import com.techjd.bookstore.models.login.response.UserResponseLogin
import com.techjd.bookstore.models.register.request.UserRequestRegister
import com.techjd.bookstore.models.register.response.UserResponseRegister
import com.techjd.bookstore.repository.UserRepository
import com.techjd.bookstore.utils.ErrorResponse
import com.techjd.bookstore.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val errorResponse: ErrorResponse
) : ViewModel() {

    private val _userRegisterResponse: MutableLiveData<NetworkResult<UserResponseRegister>> =
        MutableLiveData()
    val userRegisterResponse: LiveData<NetworkResult<UserResponseRegister>> = _userRegisterResponse

    private val _userLoginResponse: MutableLiveData<NetworkResult<UserResponseLogin>> =
        MutableLiveData()
    val userLoginResponse: LiveData<NetworkResult<UserResponseLogin>> = _userLoginResponse

    fun signUp(userRequestRegister: UserRequestRegister) = viewModelScope.launch {
        _userRegisterResponse.postValue(NetworkResult.Loading())
        val data = userRepository.signUp(userRequestRegister)
        if (data.isSuccessful) {
            _userRegisterResponse.postValue(NetworkResult.Success(data.body()!!))
        } else {
            val error = errorResponse.giveErrorResult(data.errorBody()!!)
            _userRegisterResponse.postValue(NetworkResult.Error(error.message))
        }
    }

    fun signIn(userRequestLogin: UserRequestLogin) = viewModelScope.launch {
        _userLoginResponse.postValue(NetworkResult.Loading())
        val data = userRepository.signIn(userRequestLogin)
        if (data.isSuccessful) {
            _userLoginResponse.postValue(NetworkResult.Success(data.body()!!))
        } else {
            val error = errorResponse.giveErrorResult(data.errorBody()!!)
            _userLoginResponse.postValue(NetworkResult.Error(error.message))
        }
    }
}