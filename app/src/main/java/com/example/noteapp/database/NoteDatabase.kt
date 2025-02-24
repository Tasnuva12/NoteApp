package com.example.noteapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteapp.model.Note

@Database(entities=[Note::class],version=1)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun getNoteDAO(): NoteDAO


}