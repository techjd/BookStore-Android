package com.techjd.bookstore.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techjd.bookstore.models.books.Books
import com.techjd.bookstore.repository.BuyerRepository
import com.techjd.bookstore.utils.ErrorResponse
import com.techjd.bookstore.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuyerViewModel @Inject constructor(
    private val buyerRepository: BuyerRepository,
    private val errorResponse: ErrorResponse
) :
    ViewModel() {

    private val _allBooks: MutableLiveData<NetworkResult<Books>> = MutableLiveData()
    val allBooks: LiveData<NetworkResult<Books>> = _allBooks

    fun getAllBooks() {
        viewModelScope.launch {
            _allBooks.postValue(NetworkResult.Loading())
            val data = buyerRepository.getAllBooks()
            if (data.isSuccessful) {
                _allBooks.postValue(NetworkResult.Success(data.body()!!))
            } else {
                val error = errorResponse.giveErrorResult(data.errorBody()!!)
                _allBooks.postValue(NetworkResult.Error(error.message))
            }
        }
    }

}