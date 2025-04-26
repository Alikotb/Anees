package com.example.anees.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyPeriodicWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        createNotificationChannel(applicationContext)
        showNotification(applicationContext)
        return Result.success()
    }
}
