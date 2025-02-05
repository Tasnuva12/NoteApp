package com.example.noteapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeScreenViewModel() : ViewModel() {
    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> get()=_searchText

    fun setSearchText(text:String){
        _searchText.value=text
    }


}