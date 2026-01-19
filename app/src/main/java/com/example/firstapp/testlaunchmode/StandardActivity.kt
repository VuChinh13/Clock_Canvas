package com.example.firstapp.testlaunchmode

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapp.databinding.ActivityStandardBinding

class StandardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStandardBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStandardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerClickEvent()
        binding.txtTitle.text =
            "StandardActivity Task Id: ${this.taskId}\nInstance Id: ${System.identityHashCode(this)}"
        Log.d("StandardActivity", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("StandardActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("StandardActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("StandardActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("StandardActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("StandardActivity", "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("StandardActivity", "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("StandardActivity", "onRestoreInstanceState")
    }

    fun registerClickEvent() {
        binding.btnStartStandardActivity.setOnClickListener {
            startActivity(Intent(this@StandardActivity, StandardActivity::class.java))
        }
        binding.btnStartSingleTopActivity.setOnClickListener {
            startActivity(Intent(this@StandardActivity, SingleTopActivity::class.java))
        }
        binding.btnStartSingleTaskActivity.setOnClickListener {
            startActivity(Intent(this@StandardActivity, SingleTaskActivity::class.java))
        }
        binding.btnStartSingleInstanceActivity.setOnClickListener {
            startActivity(Intent(this@StandardActivity, SingleInstanceActivity::class.java))
        }
    }
}