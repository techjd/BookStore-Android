package com.techjd.bookstore.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techjd.bookstore.db.models.Data
import com.techjd.bookstore.models.address.Address
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

    val allBooksFromCart: LiveData<List<Data>> = buyerRepository.getAllBooksOfCard

    private val _doesItemExist: MutableLiveData<Boolean> = MutableLiveData()
    val doesItemExist: LiveData<Boolean> = _doesItemExist

    val totalAmount: LiveData<Int> = buyerRepository.getTotal

    val totalItemsInCart: LiveData<Int> = buyerRepository.getTotalCountInCart

    private val _address: MutableLiveData<NetworkResult<Address>> = MutableLiveData()
    val address: LiveData<NetworkResult<Address>> = _address

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

    fun getAddress(token: String) {
        viewModelScope.launch {
            _address.postValue(NetworkResult.Loading())
            val data = buyerRepository.getAddress(token)
            if (data.isSuccessful) {
                _address.postValue(NetworkResult.Success(data.body()!!))
            } else {
                val error = errorResponse.giveErrorResult(data.errorBody()!!)
                _address.postValue(NetworkResult.Error(error.message))
            }
        }
    }

//    fun getAllBooksFromCart() {
//        viewModelScope.launch {
//            val data = buyerRepository.getAllBooksOfCard()
//            _allBooksFromCart.postValue(data.value)
//        }
//    }

//    fun getTotalPrice() {
//        viewModelScope.launch {
//            val data = buyerRepository.getTotal()
//            Log.d("TOTAL VALUE ", "getTotalPrice: ${data.value}")
//            _totalAmount.postValue(data.value)
//        }
//    }

    fun updateBook(book: Data) {
        viewModelScope.launch {
            buyerRepository.updateBook(book)
        }
    }

    fun deleteBook(book: Data) {
        viewModelScope.launch {
            buyerRepository.deleteBook(book)
        }
    }

    fun checkIfItemExists(id: String) {
        viewModelScope.launch {
            val data = buyerRepository.checkIfBookExists(id)
            _doesItemExist.postValue(data)
        }
    }

    fun insertBook(book: Data) {
        viewModelScope.launch {
            buyerRepository.insertBooks(book)
        }
    }

}