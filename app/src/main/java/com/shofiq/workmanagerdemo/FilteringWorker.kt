package com.shofiq.workmanagerdemo

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class FilteringWorker(context: Context, params: WorkerParameters): Worker(context, params) {
    override fun doWork(): Result {
        return try {
            for (i in 0 ..3000){
                Log.i("MY_TAG", "doWork: Filtering $i")
            }
            Result.success()
        }catch (e: Exception){
            Result.failure()
        }
    }
}