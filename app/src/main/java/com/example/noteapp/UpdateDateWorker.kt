package com.example.noteapp

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.noteapp.utils.DataStorage
import java.time.LocalDate

class UpdateDateWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val dateStorage = DataStorage(applicationContext)
        val today = LocalDate.now()

        // Update stored date
        dateStorage.saveDate(today)

        return Result.success()
    }
}
