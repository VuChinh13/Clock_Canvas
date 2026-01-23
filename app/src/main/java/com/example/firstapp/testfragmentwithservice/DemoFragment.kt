package com.example.firstapp.testfragmentwithservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_NOT_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.firstapp.databinding.FragmentDemoBinding

class DemoFragment : Fragment() {
    private lateinit var binding: FragmentDemoBinding
    private val resultReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val result = intent.getStringExtra("result")
            Log.d("Check", "Nhận từ Service: $result")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDemoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filter = IntentFilter(ACTION_SERVICE_RESULT)
        requireContext().registerReceiver(resultReceiver, filter, RECEIVER_NOT_EXPORTED)

        binding.btnStartService.setOnClickListener {
            val intent = Intent(requireContext(), DemoService::class.java)
            intent.putExtra("data_from_fragment", "Hello Service ")
            requireContext().startService(intent)
        }
    }
}