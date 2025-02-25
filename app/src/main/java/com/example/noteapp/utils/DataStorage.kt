package com.example.noteapp.utils

import android.content.Context
import java.time.LocalDate

// Define a class called DataStorage that takes a Context parameter.
class DataStorage(context: Context) {

    // Create a SharedPreferences object to interact with the app's shared preferences.
    // "app_prefs" is the name of the file where the preferences are stored.
    // Context.MODE_PRIVATE means only the app can access this file.
    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    // Function to save a date to shared preferences
    fun saveDate(date: LocalDate) {
        // Convert the LocalDate to a string and save it under the key "last_updated_date"
        // .apply() commits the changes asynchronously in the background.
        prefs.edit().putString("last_updated_date", date.toString()).apply()
    }

    // Function to retrieve the saved date from shared preferences
    fun getDate(): LocalDate {
        // Try to get the string value associated with the key "last_updated_date"
        // If the value doesn't exist, return null.
        val dateString = prefs.getString("last_updated_date", null)

        // If the dateString is not null, convert it back to a LocalDate object.
        // If it's null, return the current date using LocalDate.now().
        return dateString?.let { LocalDate.parse(it) } ?: LocalDate.now()
    }
}
