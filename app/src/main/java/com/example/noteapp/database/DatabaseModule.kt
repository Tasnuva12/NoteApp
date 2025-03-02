package com.example.noteapp.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context:Context): NoteDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "note_db"
        ).build()

    }
    @Provides
    fun provideNoteDAO(db: NoteDatabase): NoteDAO{
        return db.getNoteDAO()

    }

    @Provides
    @Singleton
    fun provideDateDatabase(@ApplicationContext context:Context): DateDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            DateDatabase::class.java,
            "date_db"
        ) .fallbackToDestructiveMigration() .build()

    }
    @Provides
    fun provideDateDAO(db: DateDatabase): DateDAO{
        return db.getDateDAO()
    }
}