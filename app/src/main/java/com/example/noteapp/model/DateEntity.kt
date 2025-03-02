package com.example.noteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "dates_table")
data class DateEntity(
    @PrimaryKey val id: Int=1,
    val todayDate: LocalDate,
    val dateList: List<LocalDate>
)
