package com.example.firstapp.testfragmentwithservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

const val ACTION_SERVICE_RESULT = "ACTION_SERVICE_RESULT"

class DemoService : Service() {
    override fun onCreate() {
        super.onCreate()
        Log.d("Check", "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val dataFromFragment = intent?.getStringExtra("data_from_fragment")
        Log.d("Check", "Nhận từ Fragment: $dataFromFragment")

        sendResultToFragment("Service đã xử lý xong")
        doWork()
        stopSelf()
        return START_NOT_STICKY
    }

    fun doWork() {
        for (i in 0..500) {
            Log.d("Check", i.toString())
        }
    }

    private fun sendResultToFragment(result: String) {
        val intent = Intent(ACTION_SERVICE_RESULT)
        intent.putExtra("result", result)
        sendBroadcast(intent)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Check", "onDestroy")
    }
}