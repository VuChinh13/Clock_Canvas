package com.example.firstapp.testforegroundservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.firstapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.sqrt

class TestForegroundService : Service() {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val tag = "Check"
    private val channelId = "music_service_channel"
    private var data: Data? = null
    private val NOTIFICATION_ID = 1
    private val listData = arrayListOf<Data>()
    private var valueInt = 0
    private val notificationManager by lazy {
        getSystemService(NotificationManager::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        Log.d(tag, "onCreate")
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        Log.d(tag, "onStartCommand")

        getExtra(intent)

        Log.d(tag, data?.name ?: "name null")
        Log.d(tag, data?.age.toString())
        Log.d(tag, listData.toString())
        Log.d(tag, valueInt.toString())

        startForeground(NOTIFICATION_ID, createNotification(0))
        noBlockMainThread()
        //blockMainThread()
        return START_NOT_STICKY
    }

    fun getExtra(intent: Intent?) {
        data =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getParcelableExtra("extra_data", Data::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent?.getParcelableExtra("extra_data")
            }
        val newListData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableArrayListExtra("extra_list_data", Data::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent?.getParcelableArrayListExtra("extra_list_data")
        }
        listData.addAll(newListData ?: emptyList())
        valueInt = intent?.getIntExtra("extra_int", 0) ?: 0
    }

    fun blockMainThread() {
        for (i in 2..1_000_000) {
            if (isPrime(i)) Log.d(tag, i.toString())
        }
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    fun noBlockMainThread() {
        var count = 0
        scope.launch {
            for (i in 2..1_000) {
                if (!isActive) {
                    stopForeground(STOP_FOREGROUND_REMOVE)
                    stopSelf()
                    return@launch
                }
                delay(100L)
                if (isPrime(i)) {
                    count++
                    Log.d(tag, i.toString())
                    sendToActivity(count)
                    notificationManager.notify(
                        NOTIFICATION_ID,
                        createNotification(count)
                    )
                }
            }
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
    }

    fun sendToActivity(value: Int) {
        val intent = Intent(ACTION_TASK_DONE)
        intent.putExtra("result", value)
        sendBroadcast(intent)
    }

    fun isPrime(n: Int): Boolean {
        if (n < 2) return false
        for (i in 2..sqrt(n.toDouble()).toInt()) {
            if (n % i == 0) return false
        }
        return true
    }

    private fun createNotification(count: Int): Notification {
        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.image_music)
            .setContentTitle("Service đang chạy")
            .setContentText("Số nguyên tố tìm được: $count")
            .setOnlyAlertOnce(true)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, "Music Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        Log.d(tag, "onDestroy")
    }
}