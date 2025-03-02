package com.example.noteapp.utils

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateTypeConverter {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, formatter) }
    }

    @TypeConverter
    fun fromLocalDateList(dates: List<LocalDate>?): String? {
        return dates?.joinToString(",") { it.format(formatter) }
    }

    @TypeConverter
    fun toLocalDateList(dateString: String?): List<LocalDate>? {
        return dateString?.split(",")?.map { LocalDate.parse(it, formatter) }
    }
}