package com.example.noteapp.viewmodels

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.noteapp.MyApplication
import com.example.noteapp.UpdateDateWorker
import com.example.noteapp.database.DatabaseModule
import com.example.noteapp.database.DateDatabase
import com.example.noteapp.model.DateEntity
import com.example.noteapp.repository.DateRepository
import com.example.noteapp.utils.DataStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import androidx.lifecycle.*
import com.example.noteapp.model.Note

import com.example.noteapp.repository.NoteRepository


@HiltViewModel
class HomeScreenViewModel  @Inject constructor(
    private val dateRepository: DateRepository,
    private val noteRepository: NoteRepository,

    application: Application) : ViewModel() {
    //text for search
    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> get() = _searchText

    private val _searchResults = MutableLiveData<List<Note>>()
    val searchResults: LiveData<List<Note>> get() = _searchResults

    fun searchNotes(query:String){
        _searchText.value=query
        noteRepository.searchNotes(query).observeForever{notes->
            if (notes.isNullOrEmpty()) {
                // Handle the case where there are no results
                _searchResults.value = emptyList() // or any other placeholder
            } else {
                // If there are results, update the _searchResults
                _searchResults.value = notes
            }
        }
    }

    private val _dates = MutableStateFlow<List<LocalDate>>(emptyList()) // Start with an empty list
    val dates: StateFlow<List<LocalDate>> = _dates.asStateFlow()

    private val dataStorage = DataStorage(application)

    private val handler = Handler(Looper.getMainLooper())






    // In ViewModel
    private val _currentDate = MutableStateFlow(LocalDate.now())
    val currentDate: StateFlow<LocalDate> = _currentDate.asStateFlow()

    init {


        viewModelScope.launch{loadDates()}
        startMidnightUpdateCheck()



//        scheduleDailyWorker(application)
    }




    fun setSearchText(text: String) {
        _searchText.value = text
    }
    private suspend fun loadDates() {
        val savedDateEntity = dateRepository.getSavedDates()

        val today = LocalDate.now()

        savedDateEntity?.let {
            Log.d("HomeScreenViewModel", "Retrieved saved dates: $it")
            val lastDate = it.dateList.last()


            if (lastDate < today) {
                Log.d("HomeScreenViewModel", "Last saved date is outdated, regenerating list")
                updateDates(today)  // This already updates _dates and _currentDate
            } else {
                if (_dates.value != it.dateList) {
                    _dates.value = it.dateList  // Use saved dates only if valid
                    _currentDate.value = today
                    Log.d("HomeScreenViewModel", "Loaded dates: ${it.dateList}")
                } else {
                    Log.d("HomeScreenViewModel", "Dates are already up-to-date")
                }
            }
        } ?: run {
            Log.d("HomeScreenViewModel", "No dates found, generating new list")
            updateDates(today)
        }

    }



    private suspend fun updateDates(today: LocalDate) {
        val today = LocalDate.now()
        val newDates = generateNextDates()

        dateRepository.insert(DateEntity(id = 1, todayDate = today, dateList = newDates)).also {
            Log.d("HomeScreenViewModel", "Updated database with new dates: $newDates")
        }

        _dates.value = newDates
        _currentDate.value = today
    }




    private fun generateNextDates(): List<LocalDate> {
        val dates = List(6) { LocalDate.now().plusDays(it.toLong()) }
        Log.d("HomeScreenViewModel", "Generated Dates: $dates")
        return dates
    }

    private fun startMidnightUpdateCheck() {
        val delayUntilMidnight = calculateDelayUntilMidnight()
        handler.postDelayed({

          viewModelScope.launch{
              loadDates()
          }
        }, delayUntilMidnight)
    }

//    fun scheduleDailyWorker(context: Context) {
//        val delayToMidnight = calculateDelayUntilMidnight()
//        val workRequest = PeriodicWorkRequestBuilder<UpdateDateWorker>(1, TimeUnit.DAYS)
//            .setInitialDelay(delayToMidnight, TimeUnit.MILLISECONDS)
//            .build()
//
//        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
//            "DailyDateUpdate",
//            ExistingPeriodicWorkPolicy.UPDATE,
//            workRequest
//        )
//    }

    fun calculateDelayUntilMidnight(): Long {
        val now = LocalDateTime.now()
        val midnight = now.toLocalDate().plusDays(1).atStartOfDay()
        return ChronoUnit.MILLIS.between(now, midnight)
    }




    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(null)
    }
}
