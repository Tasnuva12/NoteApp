package com.example.noteapp.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import java.time.LocalDate



class HomeScreenViewModel() : ViewModel() {

    //search
    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> get()=_searchText

    //dates

    private val _dates= MutableStateFlow(generateNextDates( ))

    val dates : StateFlow<List<LocalDate>> = _dates.asStateFlow()


    //current Date
    private val _currentDate = MutableStateFlow(LocalDate.now())
    val currentDate: StateFlow<LocalDate> = _currentDate.asStateFlow()
    fun setCurrentDate(){
        _currentDate.value= LocalDate.now()

    }




    fun setSearchText(text:String){
        _searchText.value=text
    }

    //update for next 6 date1
    fun  updateDates(){
        val lastDate=_dates.value.lastOrNull()

        val currentDate= _currentDate.value


        if(lastDate==null||currentDate.isAfter(lastDate)){
            val newDates=generateNextDates(currentDate)
            if(newDates!=_dates.value)
            {
                _dates.value = newDates

            }

        }



    }
    //show a set of date (maximum 6)
    private fun generateNextDates(startDate: LocalDate= LocalDate.now()):List<LocalDate> {

        return(0..4).map{startDate.plusDays(it.toLong())}
    }


}
