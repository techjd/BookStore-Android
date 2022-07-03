package com.techjd.bookstore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techjd.bookstore.utils.Status

class StateViewModel: ViewModel() {

    var showBottomNavigation: MutableLiveData<Status> = MutableLiveData()
}