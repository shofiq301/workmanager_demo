package com.shofiq.workmanagerdemo

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class CompressingWorker(context: Context, params: WorkerParameters): Worker(context, params) {
    override fun doWork(): Result {
        return try {
            for (i in 0..3000){
                Log.i("My_TAG", "doWork: Compressing $i")
            }
            Result.success()
        }catch (e: Exception){
            Result.failure()
        }
    }
}