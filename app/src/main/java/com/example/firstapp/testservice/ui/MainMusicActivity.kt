package com.example.firstapp.testservice.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityMainMusicBinding
import com.example.firstapp.testservice.FileItem
import com.example.firstapp.testservice.IntentExtras

class MainMusicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainMusicBinding
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val notificationGranted =
                    result[Manifest.permission.POST_NOTIFICATIONS] == true

                val audioGranted =
                    result[Manifest.permission.READ_MEDIA_AUDIO] == true

                if (notificationGranted && audioGranted) {
                    Log.d("Check","chua ok")
                    switchHomeMusicFragment()
                } else {
                    Log.d("Check","chua ok")
                    finish()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermissions()
    }

    fun switchHomeMusicFragment() {
        val homeMusicFragment = HomeMusicFragment()
        val transactionHomeMusicFragment = supportFragmentManager.beginTransaction()
        transactionHomeMusicFragment.add(R.id.fragment, homeMusicFragment)
        transactionHomeMusicFragment.commit()
    }

    fun switchPlayMusicFragment(file: FileItem) {
        val playMusicFragment = PlayMusicFragment().apply {
            arguments = Bundle().apply {
                putParcelable(IntentExtras.EXTRA_FILE, file)
            }
        }
        val transactionHomeMusicFragment = supportFragmentManager.beginTransaction()
        transactionHomeMusicFragment.add(R.id.fragment, playMusicFragment)
        transactionHomeMusicFragment.addToBackStack(null)
        transactionHomeMusicFragment.commit()
    }

    fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissions = mutableListOf<String>()

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissions.add(Manifest.permission.POST_NOTIFICATIONS)
            }
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissions.add(Manifest.permission.READ_MEDIA_AUDIO)
            }

            if (permissions.isEmpty()) {
                switchHomeMusicFragment()
            } else {
                permissionLauncher.launch(permissions.toTypedArray())
            }
        } else {
            switchHomeMusicFragment()
        }
    }
}