package com.example.anees.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.anees.utils.extensions.setAllAlarms

class AlarmResetWorker(context: Context, params: WorkerParameters)
    : Worker(context, params){
    override fun doWork(): Result {
        applicationContext.setAllAlarms()
        return Result.success()
    }
}