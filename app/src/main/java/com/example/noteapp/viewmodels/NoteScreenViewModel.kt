package com.example.noteapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.noteapp.Screen
import com.example.noteapp.model.Note
import com.example.noteapp.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteScreenViewModel @Inject constructor(private val repository: NoteRepository) :
    ViewModel() {


    //Use StateFlow for more efficient updates for large data
    private val _title = MutableStateFlow<String>("")
    val title: StateFlow<String> get() = _title

    private val _desc = MutableStateFlow<String>("")
    val desc: StateFlow<String> get() = _desc


    //list of notes
    val notes: LiveData<List<Note>> = repository.getAllNotes()

    //note ID
    private var _noteId: Int? = 0
    val noteId: Int? get() = _noteId


    // Function to update the title value
    fun setTitle(newTitle: String) {
        _title.value = newTitle
    }

    //fn: set description
    fun setDescription(description: String) {
        _desc.value = description
    }

    fun selectNote(title: String, desc: String) {
        setTitle(title)
        setDescription(desc)
    }


    //inserting  a note to database
    fun insertAndupdateToRoomDatabase(navController: NavController?) {
        val noteTitle = _title.value.trim().ifBlank { getFirstTwoWords(_desc.value.trim()) }
        val noteDescription = _desc.value.trim()

        if (noteTitle.isBlank() && noteDescription.isBlank()) {
            if (_noteId == 0) {  // If new note, just navigate back
                navController?.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.HomeScreen.route) { inclusive = true }
                }
                return
            }

            // If existing note, delete and navigate back
            deleteNote()
            navController?.navigate(Screen.HomeScreen.route) {
                popUpTo(Screen.HomeScreen.route) { inclusive = true }
            }
            return
        }

        viewModelScope.launch {
            if (_noteId != 0) {
                val existingNote = repository.getNoteById(_noteId!!)

                // If nothing changed, just navigate back
                if (existingNote?.noteTitle == noteTitle && existingNote.noteDescription == noteDescription) {
                    navController?.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.HomeScreen.route) { inclusive = true }
                    }
                    return@launch
                }

                // Update existing note
                repository.update(Note(id = _noteId!!, noteTitle = noteTitle, noteDescription = noteDescription))
            } else {
                // Insert new note
                repository.insert(Note(noteTitle = noteTitle, noteDescription = noteDescription))
            }

            navController?.navigate(Screen.HomeScreen.route) {
                popUpTo(Screen.HomeScreen.route) { inclusive = true }
            }
        }
    }



    fun getFirstTwoWords(description: String): String {
        val words = description.split(" ").filter { it.isNotBlank() }
        return if (words.size >= 2) {
            "${words[0]} ${words[1]}"
        } else {
            description  // If there are less than two words, use the full description
        }
    }

    //delete a note
    fun deleteNote() {
        noteId?.takeIf { it != 0 }?.let { id ->
            viewModelScope.launch {

                val noteToDelete = repository.getNoteById(id)


                noteToDelete?.let {
                    repository.delete(it)
                }
            }
        }
    }

    //get one note by id
    fun getOneNoteById(noteId: Int) {
        viewModelScope.launch {
            val note = repository.getNoteById(noteId)
            note?.let {
                _title.value = it.noteTitle
                _desc.value = it.noteDescription
                _noteId = it.id
            }
        }
    }





}