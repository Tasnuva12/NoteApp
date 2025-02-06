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
    @RequiresApi(Build.VERSION_CODES.O)
    private val _dates= MutableStateFlow(generateNextDates())
    @RequiresApi(Build.VERSION_CODES.O)
    val dates : StateFlow<List<LocalDate>> = _dates.asStateFlow()




    fun setSearchText(text:String){
        _searchText.value=text
    }
    @RequiresApi(Build.VERSION_CODES.O)
    //update for next 6 date
    fun  updateDates(){
        val today= LocalDate.now()
        val lastDate=_dates.value.last()
        if(today.isAfter(lastDate)){
            _dates.value=generateNextDates()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    //show a set of date (maximum 6)
    private fun generateNextDates():List<LocalDate> {
         val today= LocalDate.now()
        return(0..5).map{today.plusDays(it.toLong())}
    }


}