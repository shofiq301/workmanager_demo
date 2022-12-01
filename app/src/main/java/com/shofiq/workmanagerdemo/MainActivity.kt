package com.shofiq.workmanagerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.shofiq.workmanagerdemo.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    companion object{
        const val KEY_COUNTER = "key_counter"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnStart.setOnClickListener {
//            setOneTimeWorkRequest()
            setPeriodicWorkRequest()
        }
    }

    //Onetime work request
    private fun setOneTimeWorkRequest(){
        val workInfo = WorkManager.getInstance(applicationContext)
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val data = Data.Builder()
            .putInt(KEY_COUNTER, 600)
            .build()

        val filterRequest = OneTimeWorkRequest.Builder(FilteringWorker::class.java)
            .build()

        val compressRequest = OneTimeWorkRequest.Builder(CompressingWorker::class.java)
            .build()

        val downloadRequest = OneTimeWorkRequest.Builder(DownloadingWorker::class.java)
            .build()

        val parallelWorkers = mutableListOf<OneTimeWorkRequest>()
        parallelWorkers.add(downloadRequest)
        parallelWorkers.add(filterRequest)

        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(data)
            .build()

//        workInfo.enqueue(uploadRequest)
        //chaining workers
        workInfo
            .beginWith(parallelWorkers)
            .then(compressRequest)
            .then(uploadRequest)
            .enqueue()
        workInfo.getWorkInfoByIdLiveData(uploadRequest.id)
            .observe(this) {
                binding.txtStatus.text = it.state.name
                if (it.state.isFinished) {
                    val data = it.outputData
                    val message = data.getString(UploadWorker.KEY_WORKER)
                    binding.txtStatus.text = message
                }
            }
    }

    //Periodic work request
    private fun setPeriodicWorkRequest(){
        val workInfo = WorkManager.getInstance(applicationContext)
        val periodicWorkRequest = PeriodicWorkRequest.Builder(DownloadingWorker::class.java, 15, TimeUnit.MINUTES)
            .build()
        workInfo.enqueue(periodicWorkRequest)
    }
}
