package com.example.firstapp.testflow

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.firstapp.databinding.ActivityTestFlowBinding
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class TestFlowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestFlowBinding
    private val viewModel: TestFlowViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClickStateFlow.setOnClickListener {
            viewModel.loadUser()
        }

        binding.btnClickSharedFlow.setOnClickListener {
            viewModel.onButtonClick()
        }

        binding.btnClickSuspendCoroutine.setOnClickListener {
            viewModel.fetchDataAsync()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding.btnClickCallBackFlow.clicks().collect { count ->
                    binding.txtCount.text = count.toString()
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userState.collect { value ->
                    binding.txtContent.text = value
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toastEvent.collect { message ->
                    Toast.makeText(this@TestFlowActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toastData.collect { message ->
                    Toast.makeText(this@TestFlowActivity, message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun View.clicks(): Flow<Int> = callbackFlow {
        var count = 0
        val listener = View.OnClickListener {
            count++
            trySend(count)
        }

        setOnClickListener(listener)
        awaitClose {
            setOnClickListener(null)
        }
    }
}