package com.techjd.bookstore.viewmodels

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techjd.bookstore.utils.BottomSheetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ModalBottomSheetViewModel : ViewModel() {

    private val _clickedItem: MutableLiveData<BottomSheetOptions> = MutableLiveData()
    val clickedItem: LiveData<BottomSheetOptions> = _clickedItem

    fun setData(bottomSheetOptions: BottomSheetOptions) {
        _clickedItem.postValue(bottomSheetOptions)
    }

}