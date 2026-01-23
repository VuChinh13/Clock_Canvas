package com.example.firstapp.testfragmentwithservice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityDemoBinding

class DemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val demoFragment = DemoFragment()
        val transactionHomeMusicFragment = supportFragmentManager.beginTransaction()
        transactionHomeMusicFragment.add(R.id.fragmentDemo, demoFragment)
        transactionHomeMusicFragment.commit()
    }
}