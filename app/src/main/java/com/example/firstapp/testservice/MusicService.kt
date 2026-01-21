package com.example.firstapp.testservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.firstapp.R

class MusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private var file: FileItem? = null
    private var isPlayingMusic = false
    private val ACTION_STOP_FOREGROUND = "ACTION_STOP_FOREGROUND"
    private val CHANNEL_ID = "music_service_channel"
    private val NOTIFICATION_ID = 1
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
        Log.d("Check", "Co vao ham")
        if (intent?.action == ACTION_STOP_FOREGROUND) {
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
            return START_NOT_STICKY
        }

        file = intent?.getParcelableExtra(IntentExtras.EXTRA_FILE, FileItem::class.java)
        startForeground(NOTIFICATION_ID, createNotification())
        file?.let {
            playMusic(it.path)
        }
        return START_NOT_STICKY
    }

    private fun playMusic(path: String) {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer()
            } else {
                mediaPlayer?.reset()
            }
            val uri = Uri.parse(path)
            val pfd = contentResolver.openFileDescriptor(uri, "r")
            pfd?.use {
                mediaPlayer?.apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    setDataSource(it.fileDescriptor)
                    setOnPreparedListener {
                        start()
                        isPlayingMusic = true
                    }
                    prepareAsync()
                }
            }
        } catch (e: Exception) {
            Log.e("MusicService", "Play error: ${e.message}", e)
        }
    }

    private fun createNotification(): Notification {
        val dismissIntent = Intent(this, MusicService::class.java).apply {
            action = ACTION_STOP_FOREGROUND
        }
        val dismissPendingIntent = PendingIntent.getService(
            this,
            0,
            dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.image_music)
            .setContentTitle("Trình phát nhạc")
            .setContentText(file?.name)
            .setDeleteIntent(dismissPendingIntent)
            .build()
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

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.reset()
            it.release()
        }
        mediaPlayer = null
        isPlayingMusic = false
        Log.d("MusicService", "Service destroyed, MediaPlayer released")
    }
}