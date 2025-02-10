package com.example.noteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes")


data class Note(
    @PrimaryKey(autoGenerate = true)
    val id:Int =0,
    val noteTitle:String,
    val noteDescription:String
): Serializable
