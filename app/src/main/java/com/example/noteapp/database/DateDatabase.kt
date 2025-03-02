package com.example.noteapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

import androidx.room.TypeConverters
import com.example.noteapp.model.DateEntity
import com.example.noteapp.utils.DateTypeConverter

@Database(entities = [DateEntity::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class DateDatabase: RoomDatabase() {
 abstract fun  getDateDAO(): DateDAO


}