package com.example.noteapp.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate


class HomeScreenViewModel() : ViewModel() {

    //search
    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> get()=_searchText

    //dates

    private val _dates= MutableStateFlow(generateNextDates())

    val dates : StateFlow<List<LocalDate>> = _dates.asStateFlow()




    fun setSearchText(text:String){
        _searchText.value=text
    }

    //update for next 6 date
    fun  updateDates(){
        val today= LocalDate.now()
        val lastDate = _dates.value.lastOrNull() ?: today
        if(today.isAfter(lastDate)){
            _dates.value=generateNextDates(today)
        }
    }
    //show a set of date (maximum 6)
    private fun generateNextDates(startDate: LocalDate= LocalDate.now()):List<LocalDate> {

        return(0..4).map{startDate.plusDays(it.toLong())}
    }


}