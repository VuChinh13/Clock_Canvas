package com.example.firstapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.firstapp.databinding.ActivityTestCoroutineBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sqrt

class TestCoroutineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestCoroutineBinding
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestCoroutineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // checkBlockUIThread()
        // checkCoroutine()

        // demoStatusJob()
        // demoJoinJob()
        // demoAsync2()

        binding.btnClick2.setOnClickListener {
            Toast.makeText(this, "This is Button 2", Toast.LENGTH_SHORT).show()
        }
    }

    fun checkBlockUIThread() {
        binding.btnClick1.setOnClickListener {
            for (i in 2..500_000) {
                if (isPrime((i))) count++
                Log.d("Check", count.toString())
            }
        }
    }

    fun checkCoroutine() {
        binding.btnClick1.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                for (i in 2..1_000_000) {
                    if (isPrime(i)) count++
                    Log.d("Check", count.toString())
                }
            }
        }
    }

    fun isPrime(n: Int): Boolean {
        if (n < 2) return false
        for (i in 2..sqrt(n.toDouble()).toInt()) {
            if (n % i == 0) return true
        }
        return true
    }

    fun demoStatusJob() {
        lifecycleScope.launch {
            val job = CoroutineScope(Dispatchers.Default).launch {
                Log.d("CheckJob", "NO_DONE")
                delay(2000)
                Log.d("CheckJob", "DONE")
            }
            Log.d("CheckJob", "isActive = ${job.isActive}")
            Log.d("CheckJob", "isCancelled = ${job.isCancelled}")
            Log.d("CheckJob", "isCompleted = ${job.isCompleted}")
        }
    }

    fun demoJoinJob() {
        lifecycleScope.launch(Dispatchers.Main) {
            launch {
                Log.d("CheckJob", "Background job done 1 ")
                delay(2000)
                Log.d("CheckJob", "Background job done")
            }

            Log.d("CheckJob", "Waiting for job...")
            Log.d("CheckJob", "Job finished, continue UI")
        }
    }

    fun demoAsync1() {
        lifecycleScope.launch(Dispatchers.Default) {

            var countPrime1 = 0
            val prime1 = async {
                for (i in 2..1_000) {
                    if (isPrime(i)) countPrime1++
                    Log.d("CheckJob", "Count 1: $countPrime1")
                }
                countPrime1
            }

            var countPrime2 = 0
            val prime2 = async {
                for (i in 2..1_000) {
                    if (isPrime(i)) countPrime2++
                    Log.d("CheckJob", "Count 2: $countPrime2")
                }
                countPrime2
            }

            val value1 = prime1.await()
            val value2 = prime2.await()

            Log.d("CheckJob", "Result: $value1")
            Log.d("CheckJob", "Result: $value2")
        }
    }

    fun demoAsync2() {
        lifecycleScope.launch(Dispatchers.Default) {

            var countPrime1 = 0
            val prime1 = async {
                for (i in 2..1_000) {
                    if (isPrime(i)) countPrime1++
                    Log.d("CheckJob", "Count 1: $countPrime1")
                }
                countPrime1
            }.await()

            var countPrime2 = 0
            val prime2 = async {
                for (i in 2..1_000) {
                    if (isPrime(i)) countPrime2++
                    Log.d("CheckJob", "Count 2: $countPrime2")
                }
                countPrime2
            }.await()

            Log.d("CheckJob", "Result: $prime1")
            Log.d("CheckJob", "Result: $prime2")
        }
    }
}