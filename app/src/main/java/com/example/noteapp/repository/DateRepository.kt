package com.example.noteapp.repository

import com.example.noteapp.database.DateDatabase
import com.example.noteapp.model.DateEntity
import javax.inject.Inject

class DateRepository @Inject constructor(private val db: DateDatabase) {
    suspend fun insert(dateEntity: DateEntity)=db.getDateDAO().savedDates(dateEntity)
    suspend fun getSavedDates()=db.getDateDAO().getSavedDates()

}