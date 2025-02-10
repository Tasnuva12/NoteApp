package com.example.noteapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteapp.model.Note

@Database(entities=[Note::class],version=1)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun getNoteDAO(): NoteDAO

    companion object {
        //static
        @Volatile //this annotation ensures changes made in one thread is immediately visible to other threads
        private  var instance: NoteDatabase?=null
        private val LOCK=Any()

        operator fun invoke (context:Context)=instance?:
        synchronized(LOCK){
            instance?:
            createDatabase(context).also{
                instance=it
            }
        }

        private fun createDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_db"

            ).build()



    }
}