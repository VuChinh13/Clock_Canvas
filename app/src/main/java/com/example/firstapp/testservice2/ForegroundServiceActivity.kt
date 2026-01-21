package com.example.firstapp.testservice2

import android.app.ForegroundServiceStartNotAllowedException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapp.databinding.ActivityService1Binding

class ForegroundServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityService1Binding
    private lateinit var listData: MutableList<Data>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityService1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initListData()

        binding.btnStartForeground.setOnClickListener {
            try {
                val data =
                    Data(
                        binding.etName.text.toString(),
                        binding.etAge.text.toString().toIntOrNull() ?: 0
                    )

                val testForegroundServiceIntent = Intent(this, TestForegroundService::class.java)
                testForegroundServiceIntent.putExtra("extra_data", data)
                testForegroundServiceIntent.putExtra("extra_list_data", ArrayList(listData))
                testForegroundServiceIntent.putExtra("extra_int", 10)

                startForegroundService(testForegroundServiceIntent)
            } catch (e: ForegroundServiceStartNotAllowedException) {
                Log.e("Check", e.message.toString())
            }
        }

        binding.btnStopForeground.setOnClickListener {
            val testForegroundServiceIntent = Intent(this, TestForegroundService::class.java)
            stopService(testForegroundServiceIntent)
        }

        binding.btnToast.setOnClickListener {
            Toast.makeText(this, "Đây là thông báo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initListData() {
        listData = mutableListOf()
        listData.add(Data("long1", 10))
        listData.add(Data("long2", 11))
        listData.add(Data("long3", 12))
    }
}