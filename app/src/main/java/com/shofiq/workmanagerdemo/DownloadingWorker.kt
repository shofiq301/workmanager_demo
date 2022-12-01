package com.shofiq.workmanagerdemo

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class DownloadingWorker(context: Context, parameters: WorkerParameters): Worker(context, parameters) {

    companion object{
        const val DOWNLOAD_REQUEST_KEY = "key_download"
    }

    override fun doWork(): Result {
        return try {
            for (i in 0..300){
                Log.i("MY_TAG", "doWork: Downloading $i")
            }
            val time = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val currentDate = time.format(Date())
            Log.i("MY_TAG", "doWork: completed at $currentDate")
            Result.success()
        }catch (e: Exception){
            Result.failure()
        }
    }
}