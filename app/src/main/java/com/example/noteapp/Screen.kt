package com.example.noteapp

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object HomeScreen:Screen(){

        val route="home_screen"
    }
    @Serializable
    data object NoteScreen:Screen(){
        val route="note_screen"
    }

}