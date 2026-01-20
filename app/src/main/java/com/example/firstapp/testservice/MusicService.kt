package com.example.firstapp.testservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.firstapp.R

class MusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private val CHANNEL_ID = "music_service_channel"
    private val NOTIFICATION_ID = 1
    private var file: FileItem? = null
    private val fileList = mutableListOf<FileItem>()
    private var isPlaying = false
    private val notificationManager by lazy {
        getSystemService(NotificationManager::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        file = intent?.getParcelableExtra(IntentExtras.EXTRA_FILE, FileItem::class.java)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, "Music Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun creatNotification() {
        NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.image_music)
            .setContentTitle(file?.name)
            .setContentText("Bài hát của tôi")
            .setLargeIcon()
            .build()
    }
}