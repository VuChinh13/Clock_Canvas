package com.example.firstapp.testdoublefragment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityDoubleFragmentMainBinding
import com.example.firstapp.testfragment.FirstFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DoubleFragmentMainActivity : AppCompatActivity() {
    private val sumState = MutableStateFlow(0)
    private val valueState = MutableStateFlow(0)
    private lateinit var binding: ActivityDoubleFragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoubleFragmentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentA = FragmentA()
        val transactionAFragment = supportFragmentManager.beginTransaction()
        transactionAFragment.add(R.id.fragmentFirst, fragmentA)
        transactionAFragment.commit()

        val fragmentB = FragmentB()
        val transactionBFragment = supportFragmentManager.beginTransaction()
        transactionBFragment.add(R.id.fragmentSecondary, fragmentB)
        transactionBFragment.commit()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sumState.collect { value ->
                    fragmentB.updateSum(value)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                valueState.collect { value ->
                    fragmentA.updateNewValue(value)
                }
            }
        }
    }

    fun updateState(newValue: Int) {
        sumState.value = newValue
    }

    fun updateValue(newValue: Int) {
        valueState.value = newValue
    }
}