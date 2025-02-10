package com.example.noteapp.repository

import com.example.noteapp.database.NoteDatabase
import com.example.noteapp.model.Note

class NoteRepository(private val db: NoteDatabase) {
    suspend fun insert(note:Note)=db.getNoteDAO().insertNote(note)
    suspend fun delete (note:Note)=db.getNoteDAO().deleteNote(note)
    suspend fun update(note:Note)=db.getNoteDAO().updateNote(note)


    fun getAllNotes()=db.getNoteDAO().getAllNotes()
    fun searchNotes(query:String)=db.getNoteDAO().searchNote(query)

}