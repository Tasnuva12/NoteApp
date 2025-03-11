package com.example.noteapp.viewmodels

import androidx.lifecycle.LiveData
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
    private var _noteId: Int? = null
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
        var noteTitle = _title.value?.trim() ?: ""
        val noteDescription = _desc.value?.trim() ?: ""

        // If the title is empty, set the title to the first two words of the description
        if (noteTitle.isBlank()) {
            noteTitle = getFirstTwoWords(noteDescription)
        }

        // If both title and description are blank, navigate back to HomeScreen
        if (noteTitle.isBlank() && noteDescription.isBlank()) {
            // If noteId is -1, we simply navigate to the Home screen without any other actions
            if (_noteId == -1) {
                navController?.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.HomeScreen.route) { inclusive = true }
                }
                return // Stop execution here
            }

            // If the noteId is not -1 (meaning we are updating an existing note), delete the note
            _noteId?.takeIf { it != -1 }?.let { deleteNote() }

            navController?.navigate(Screen.HomeScreen.route) {
                popUpTo(Screen.HomeScreen.route) { inclusive = true }
            }
            return // Stop execution here
        }


        // Start coroutine for database operation
        viewModelScope.launch {
            // Get the existing note if the noteId is not -1
            val existingNote = if (_noteId != -1) {
                repository.getNoteById(_noteId!!)  // Move this inside the coroutine
            } else {
                null
            }

            // If the note is identical to the existing note (no changes), navigate back
            if (_noteId != -1 && existingNote?.noteTitle == noteTitle && existingNote?.noteDescription == noteDescription) {
                navController?.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.HomeScreen.route) { inclusive = true }
                }
                return@launch
            }

            // Insert a new note if _noteId is -1, or update an existing note if data has changed
            if (_noteId == -1) {
                if (noteTitle.isNotBlank() || noteDescription.isNotBlank()) {
                    val insertNote = Note(noteTitle = noteTitle, noteDescription = noteDescription)
                    repository.insert(insertNote)
                }
            } else {
                // Update existing note only if title or description has changed
                val updateNote = Note(id = _noteId!!, noteTitle = noteTitle, noteDescription = noteDescription)
                repository.update(updateNote)
            }

            // Navigate back to HomeScreen after insert or update operation
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
        noteId?.takeIf { it != -1 }?.let { id ->
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

    }


}