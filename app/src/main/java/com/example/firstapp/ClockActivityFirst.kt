package com.example.firstapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapp.databinding.ActivityClockFirstBinding

class ClockActivityFirst : AppCompatActivity() {
    private lateinit var binding: ActivityClockFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClockFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}