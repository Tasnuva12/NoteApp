package com.example.noteapp.repository

import androidx.lifecycle.LiveData
import com.example.noteapp.database.NoteDatabase
import com.example.noteapp.model.Note
import javax.inject.Inject

class NoteRepository @Inject constructor(private val db: NoteDatabase) {
    suspend fun insert(note: Note) = db.getNoteDAO().insertNote(note)

    suspend fun delete(note: Note) = db.getNoteDAO().deleteNote(note)


    suspend fun update(note: Note) {
        // Create a new note with a new ID (you can generate the ID if necessary, but using Room's auto-increment should suffice)
        val updatedNote = note.copy(id = generateNewId())  // `generateNewId()` can be a function to create a new unique ID

        // Insert the updated note with the new ID
        insert(updatedNote)

        // Delete the old note
        delete(note)
    }


    suspend fun getNoteById(id: Int): Note? {
        return db.getNoteDAO().getNoteById(id)
    }

    // No need to mark these as suspend
    fun getAllNotes() = db.getNoteDAO().getAllNotes()

    fun searchNotes(query: String?): LiveData<List<Note>> {
        return db.getNoteDAO().searchNote("%$query%")
    }

    // Optionally, generate a new ID for the updated note
    private fun generateNewId(): Int {
        // Room should automatically generate a new ID (auto-increment) when inserting, but you can manually handle it if needed
        // For example, you could increment the highest existing ID in the database
        return (System.currentTimeMillis() % Int.MAX_VALUE).toInt()  // Simple example to generate a unique ID
    }
}

