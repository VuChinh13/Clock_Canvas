package com.example.firstapp.testforegroundservice

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapp.databinding.ActivityService1Binding

const val ACTION_TASK_DONE = "com.example.firstapp.ACTION_TASK_DONE"

class ForegroundServiceActivity : AppCompatActivity() {
    private val taskDoneReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val result = intent?.getIntExtra("result", 0)
            binding.tvCount.text = result.toString()
        }
    }
    private lateinit var airplaneModeReceiver: AirplaneModeReceiver
    private lateinit var binding: ActivityService1Binding
    private lateinit var listData: MutableList<Data>

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityService1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(
                taskDoneReceiver,
                IntentFilter(ACTION_TASK_DONE),
                RECEIVER_NOT_EXPORTED
            )
        } else {
            registerReceiver(
                taskDoneReceiver,
                IntentFilter(ACTION_TASK_DONE)
            )
        }

        initListData()
        startTestForegroundService()
        stopTestForegroundService()

        binding.btnToast.setOnClickListener {
            Toast.makeText(this, "Đây là thông báo", Toast.LENGTH_SHORT).show()
        }

        registerBroadCast()
    }

    private fun registerBroadCast() {
        airplaneModeReceiver = AirplaneModeReceiver { isOn ->
            if (isOn) {
                Toast.makeText(this, "Bật chế độ máy bay", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Tắt chế độ máy bay", Toast.LENGTH_SHORT).show()
            }
        }
        registerReceiver(airplaneModeReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startTestForegroundService() {
        binding.btnStartForeground.setOnClickListener {
            val data = Data(
                binding.etName.text.toString(),
                binding.etAge.text.toString().toIntOrNull() ?: 0
            )

            val testForegroundServiceIntent = Intent(this, TestForegroundService::class.java)
            testForegroundServiceIntent.putExtra("extra_data", data)
            testForegroundServiceIntent.putExtra("extra_list_data", ArrayList(listData))
            testForegroundServiceIntent.putExtra("extra_int", 10)

            startForegroundService(testForegroundServiceIntent)
            //startService(testForegroundServiceIntent)
        }
    }

    private fun stopTestForegroundService() {
        binding.btnStopForeground.setOnClickListener {
            val testForegroundServiceIntent = Intent(this, TestForegroundService::class.java)
            stopService(testForegroundServiceIntent)
        }
    }

    private fun initListData() {
        listData = mutableListOf()
        listData.add(Data("long1", 10))
        listData.add(Data("long2", 11))
        listData.add(Data("long3", 12))
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Check", "onDestroy Activity")
        // unregisterReceiver(taskDoneReceiver)
        unregisterReceiver(airplaneModeReceiver)
    }
}