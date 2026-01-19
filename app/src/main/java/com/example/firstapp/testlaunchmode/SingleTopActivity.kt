package com.example.firstapp.testlaunchmode

import android.annotation.SuppressLint
import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapp.databinding.ActivitySingleTopBinding

class SingleTopActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingleTopBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingleTopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerClickEvent()
        binding.txtTitle.text =
            "SingleTopActivity Task Id: ${this.taskId}\nInstance Id: ${System.identityHashCode(this)}"
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("Check","OnNewIntent")
    }

    fun registerClickEvent() {
        binding.btnStartStandardActivity.setOnClickListener {
            startActivity(Intent(this@SingleTopActivity, StandardActivity::class.java))
        }
        binding.btnStartSingleTopActivity.setOnClickListener {
            startActivity(Intent(this@SingleTopActivity, SingleTopActivity::class.java))
        }
        binding.btnStartSingleTaskActivity.setOnClickListener {
            startActivity(Intent(this@SingleTopActivity, SingleTaskActivity::class.java))
        }
        binding.btnStartSingleInstanceActivity.setOnClickListener {
            startActivity(Intent(this@SingleTopActivity, SingleInstanceActivity::class.java))
        }
    }
}