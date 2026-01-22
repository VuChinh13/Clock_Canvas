package com.example.firstapp.testfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firstapp.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.setFragmentResultListener(
            "request_key",
            viewLifecycleOwner
        ) { _, bundle ->
            val result = bundle.getInt("extra_result")
            binding.tvResult.text = result.toString()
        }

        binding.btSwitchFragment.setOnClickListener {
            val user = User(
                binding.etName.text.toString(),
                binding.etAge.text.toString().toIntOrNull() ?: 0
            )
            (activity as? TestFragmentActivity)?.switchSecondaryFragment(
                user,
                binding.etValueFirst.text.toString().toIntOrNull() ?: 0,
                binding.etValueSecondary.text.toString().toIntOrNull() ?: 0
            )
        }
    }
}