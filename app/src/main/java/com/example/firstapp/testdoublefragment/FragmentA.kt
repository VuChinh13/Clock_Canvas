package com.example.firstapp.testdoublefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firstapp.R
import com.example.firstapp.databinding.FragmentABinding

class FragmentA : Fragment() {
    private lateinit var binding: FragmentABinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentABinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSum.setOnClickListener {
            val firstValue = binding.etValueFirst.text.toString().toIntOrNull() ?: 0
            val firstSecondary = binding.etValueSecondary.text.toString().toIntOrNull() ?: 0
            val sum = firstSecondary + firstValue
            (activity as? DoubleFragmentMainActivity)?.updateState(sum)
        }
    }

    fun updateNewValue(value: Int) {
        binding.tvSum.text = value.toString()
    }
}