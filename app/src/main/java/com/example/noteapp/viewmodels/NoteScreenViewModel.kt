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
class NoteScreenViewModel @Inject constructor( private val repository: NoteRepository): ViewModel() {




    //Use StateFlow for more efficient updates for large data
    private val _title = MutableStateFlow<String>("")
    val title: StateFlow<String> get() = _title

    private val _desc = MutableStateFlow<String>("")
    val desc: StateFlow<String> get() = _desc


    //list of notes
    val notes: LiveData<List<Note>> = repository.getAllNotes()

    //note ID
    private var _noteId:Int?=null
    val noteId:Int? get() = _noteId



    // Function to update the title value
    fun setTitle(newTitle: String) {
        _title.value = newTitle
    }
    //fn: set description
    fun setDescription(description:String){
        _desc.value=description
    }

    fun selectNote(title:String,desc:String){
        setTitle(title)
        setDescription(desc)
    }


    //inserting  a note to database
    fun insertAndupdateToRoomDatabase(navController: NavController?){
          var noteTitle= _title.value?.trim()?: ""
           val noteDescription= _desc.value?.trim()?:""

        // If the title is empty, set the title to the first two words of the description
        if (noteTitle.isBlank()) {
            noteTitle = getFirstTwoWords(noteDescription)
        }

        if(noteTitle.isBlank()  && noteDescription.isBlank()){
            _noteId?.let { deleteNote() }
            navController?.navigate(Screen.HomeScreen.route){
                popUpTo(Screen.HomeScreen.route){inclusive=true}
            }
            return
        }
        val note =Note(

            noteTitle= noteTitle,
            noteDescription = noteDescription
        )

        // If the new note is identical to the existing note, don't do anything
        if (_noteId != null && note == Note(id = _noteId!!, noteTitle = _title.value, noteDescription = _desc.value)) {
            // No changes detected, so return early
            navController?.navigate(Screen.HomeScreen.route){
                popUpTo(Screen.HomeScreen.route){inclusive=true}
            }
            return
        }

        viewModelScope.launch{

            if(_noteId==null ){
                val insertNote=Note(noteTitle=noteTitle, noteDescription = noteDescription)
                repository.insert(insertNote)
            }
            else {
                val updateNote=Note(id=_noteId!!,noteTitle=noteTitle,noteDescription=noteDescription)
                repository.update(updateNote)

            }
        }
        navController?.navigate(Screen.HomeScreen.route){
            popUpTo(Screen.HomeScreen.route){inclusive=true}
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
        noteId?.let { id ->
            viewModelScope.launch {

                val noteToDelete = repository.getNoteById(id)


                noteToDelete?.let {
                    repository.delete(it)
                }
            }
        }
    }
    //get one note by id
    fun getOneNoteById(noteId:Int){

    }




}