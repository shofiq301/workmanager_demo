package com.shofiq.workmanagerdemo

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class UploadWorker(context: Context, params: WorkerParameters): Worker(context, params) {

    companion object{
        const val KEY_WORKER = "key_worker"
    }

    override fun doWork(): Result {
        return try {
            val count = inputData.getInt(MainActivity.KEY_COUNTER, 0)
            for (i in 0 until count){
                Log.i("MY_TAG", "doWork: Uploading $i")
            }
            val time = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val currentDate = time.format(Date())
            val outputData = Data.Builder()
                .putString(KEY_WORKER, currentDate)
                .build()
            Result.success(outputData)
        }catch (e: Exception){
            Result.failure()
        }
    }
}