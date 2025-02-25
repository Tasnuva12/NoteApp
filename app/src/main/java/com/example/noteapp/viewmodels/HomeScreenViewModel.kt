package com.example.noteapp.viewmodels

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.noteapp.MyApplication
import com.example.noteapp.UpdateDateWorker
import com.example.noteapp.utils.DataStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel  @Inject constructor(application: Application) : ViewModel() {

    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> get() = _searchText

    private val _dates = MutableStateFlow(generateNextDates())
    val dates: StateFlow<List<LocalDate>> = _dates.asStateFlow()

    private val dataStorage = DataStorage(application)

    private val handler = Handler(Looper.getMainLooper())

    // In ViewModel
    private val _currentDate = MutableStateFlow(LocalDate.now())
    val currentDate: StateFlow<LocalDate> = _currentDate.asStateFlow()

    init {
        updateDates()
        startMidnightUpdateCheck()
        scheduleDailyWorker(application)
    }

    fun setSearchText(text: String) {
        _searchText.value = text
    }

    fun updateDates() {
        val today = LocalDate.now()
        _currentDate.value = today
        val lastSavedDate = dataStorage.getDate()


        val lastDate=_dates.value.lastOrNull()
        if (lastDate == null || lastDate < today) {
            _dates.value = generateNextDates()
        }
        if (lastSavedDate != today) {
            dataStorage.saveDate(today)

        }

    }

    private fun generateNextDates(): List<LocalDate> {
        return List(5) { LocalDate.now().plusDays(it.toLong()) }
    }

    private fun startMidnightUpdateCheck() {
        val delayUntilMidnight = calculateDelayUntilMidnight()
        handler.postDelayed({ updateDates() }, delayUntilMidnight)
    }

    fun scheduleDailyWorker(context: Context) {
        val delayToMidnight = calculateDelayUntilMidnight()
        val workRequest = PeriodicWorkRequestBuilder<UpdateDateWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(delayToMidnight, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "DailyDateUpdate",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

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
