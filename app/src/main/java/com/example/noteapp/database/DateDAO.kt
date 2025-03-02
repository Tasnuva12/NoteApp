package com.example.noteapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.noteapp.model.DateEntity

@Dao
interface DateDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savedDates(dateEntity: DateEntity)


    //suspend is required for functions that perform I/O operations on the main thread
    //  @Insert, @Update, and @Delete involve database write operations, which are time-consuming. Room requires
    // them to be marked as suspend to ensure they run on a background thread when using Kotlin Coroutines.

    @Query ("SELECT * FROM dates_table WHERE  id=1 LIMIT 1")
    suspend fun  getSavedDates(): DateEntity?

}



