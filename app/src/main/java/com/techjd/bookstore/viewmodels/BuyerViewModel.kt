package com.techjd.bookstore.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.lifecycle.*
import com.BookStoreApplication
import com.techjd.bookstore.db.models.Data
import com.techjd.bookstore.models.address.Address
import com.techjd.bookstore.models.address.request.InputAddress
import com.techjd.bookstore.models.allOrders.AllOrder
import com.techjd.bookstore.models.books.Books
import com.techjd.bookstore.models.history.History
import com.techjd.bookstore.models.orders.request.OrdersRequest
import com.techjd.bookstore.models.orders.response.Orders
import com.techjd.bookstore.models.paymentstatus.PaymentStatusRequest
import com.techjd.bookstore.repository.BuyerRepository
import com.techjd.bookstore.utils.ErrorResponse
import com.techjd.bookstore.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class BuyerViewModel @Inject constructor(
    private val buyerRepository: BuyerRepository,
    private val errorResponse: ErrorResponse,
    val app: Application
) : AndroidViewModel(app) {

    private val _allBooks: MutableLiveData<NetworkResult<Books>> = MutableLiveData()
    val allBooks: LiveData<NetworkResult<Books>> = _allBooks

    val allBooksFromCart: LiveData<List<Data>> = buyerRepository.getAllBooksOfCard

    private val _doesItemExist: MutableLiveData<Boolean> = MutableLiveData()
    val doesItemExist: LiveData<Boolean> = _doesItemExist

    val totalAmount: LiveData<Int> = buyerRepository.getTotal

    val totalItemsInCart: LiveData<Int> = buyerRepository.getTotalCountInCart

    private val _address: MutableLiveData<NetworkResult<Address>> = MutableLiveData()
    val address: LiveData<NetworkResult<Address>> = _address

    private val _addAddress: MutableLiveData<NetworkResult<Address>> = MutableLiveData()
    val addAddress: LiveData<NetworkResult<Address>> = _addAddress

    private val _orders: MutableLiveData<NetworkResult<Orders>> = MutableLiveData()
    val orders: LiveData<NetworkResult<Orders>> = _orders

    private val _paymentUpdate: MutableLiveData<NetworkResult<Orders>> = MutableLiveData()
    val paymentUpdate: LiveData<NetworkResult<Orders>> = _paymentUpdate

    private val _allOrders: MutableLiveData<NetworkResult<AllOrder>> = MutableLiveData()
    val allOrders: LiveData<NetworkResult<AllOrder>> = _allOrders

    private val _history: MutableLiveData<NetworkResult<AllOrder>> = MutableLiveData()
    val history: LiveData<NetworkResult<AllOrder>> = _history

    private val _cartCount: MutableLiveData<Int> = MutableLiveData()
    val cartCount: LiveData<Int> = _cartCount

    private var _forSameSeller: MutableLiveData<Data> = MutableLiveData()
    val forSameSeller: LiveData<Data> = _forSameSeller

    var orderId: MutableLiveData<String> = MutableLiveData()

    fun getAllBooks() {
        viewModelScope.launch {
            _allBooks.postValue(NetworkResult.Loading())
            try {
                if (hasInternetConnection()) {
                    val data = buyerRepository.getAllBooks()
                    if (data.isSuccessful) {
                        _allBooks.postValue(NetworkResult.Success(data.body()!!))
                    } else {
                        val error = errorResponse.giveErrorResult(data.errorBody()!!)
                        _allBooks.postValue(NetworkResult.Error(error.message))
                    }
                } else {
                    _allBooks.postValue(NetworkResult.Error("No Internet Connection"))
                }
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> _allBooks.postValue(NetworkResult.Error("Network Failure"))
                    else -> _allBooks.postValue(NetworkResult.Error("Some Error Occurred , Please Try Again Later"))
                }
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
                Log.d("ERRRO", "getAddress: ${error}")
                _address.postValue(NetworkResult.Error(error.message))
            }
        }
    }

    fun addAddress(token: String, inputAddress: InputAddress) {
        viewModelScope.launch {
            _addAddress.postValue(NetworkResult.Loading())
            val data = buyerRepository.addAddress(token, inputAddress)
            if (data.isSuccessful) {
                _addAddress.postValue(NetworkResult.Success(data.body()!!))
            } else {
                val error = errorResponse.giveErrorResult(data.errorBody()!!)
                _addAddress.postValue(NetworkResult.Error(error.message))
            }
        }
    }

    fun placeOrder(token: String, ordersRequest: OrdersRequest) {
        viewModelScope.launch {
            _orders.postValue(NetworkResult.Loading())
            val data = buyerRepository.placeOrder(token, ordersRequest)
            if (data.isSuccessful) {
                _orders.postValue(NetworkResult.Success(data.body()!!))
            } else {
                val error = errorResponse.giveErrorResult(data.errorBody()!!)
                _orders.postValue(NetworkResult.Error(error.message))
            }
        }
    }

    fun updatePayment(token: String, paymentStatusRequest: PaymentStatusRequest) {
        viewModelScope.launch {
            _paymentUpdate.postValue(NetworkResult.Loading())
            val data = buyerRepository.updatePaymentStatus(token, paymentStatusRequest)
            if (data.isSuccessful) {
                _paymentUpdate.postValue(NetworkResult.Success(data.body()!!))
            } else {
                val error = errorResponse.giveErrorResult(data.errorBody()!!)
                _paymentUpdate.postValue(NetworkResult.Error(error.message))
            }
        }
    }

    fun getAllOrders(token: String) {
        viewModelScope.launch {
            _allOrders.postValue(NetworkResult.Loading())
            val data = buyerRepository.getOrders(token)
            if (data.isSuccessful) {
                _allOrders.postValue(NetworkResult.Success(data.body()!!))
            } else {
                val error = errorResponse.giveErrorResult(data.errorBody()!!)
                _allOrders.postValue(NetworkResult.Error(error.message))
            }
        }
    }

    fun getHistory(token: String) {
        viewModelScope.launch {
            _history.postValue(NetworkResult.Loading())
            val data = buyerRepository.getHistory(token)
            if (data.isSuccessful) {
                _history.postValue(NetworkResult.Success(data.body()!!))
            } else {
                val error = errorResponse.giveErrorResult(data.errorBody()!!)
                _history.postValue(NetworkResult.Error(error.message))
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

    fun deleteAllBooks() {
        viewModelScope.launch {
            buyerRepository.deleteAllBooks()
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

    fun cartCount() {
        viewModelScope.launch {
            val data = buyerRepository.getCartCount()
            _cartCount.postValue(data)
        }
    }

    fun isSameSeller() {
        viewModelScope.launch {
            val data = buyerRepository.checkIfUserIsInsertingBooksWithSameSeller()
            _forSameSeller.postValue(data)
        }
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<BookStoreApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
            return false
        }
    }

}