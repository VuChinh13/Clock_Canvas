package com.example.firstapp.testlaunchmode

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapp.databinding.ActivitySingleInstanceBinding

class SingleInstanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingleInstanceBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingleInstanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerClickEvent()
        binding.txtTitle.text =
            "SingleInstanceActivity Task Id: ${this.taskId}\nInstance Id: ${
                System.identityHashCode(
                    this
                )
            }"
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("Check","OnNewIntent")
    }

    fun registerClickEvent() {
        binding.btnStartStandardActivity.setOnClickListener {
            startActivity(Intent(this@SingleInstanceActivity, StandardActivity::class.java))
        }
        binding.btnStartSingleTopActivity.setOnClickListener {
            startActivity(Intent(this@SingleInstanceActivity, SingleTopActivity::class.java))
        }
        binding.btnStartSingleTaskActivity.setOnClickListener {
            startActivity(Intent(this@SingleInstanceActivity, SingleTaskActivity::class.java))
        }
        binding.btnStartSingleInstanceActivity.setOnClickListener {
            startActivity(Intent(this@SingleInstanceActivity, SingleInstanceActivity::class.java))
        }
    }
}