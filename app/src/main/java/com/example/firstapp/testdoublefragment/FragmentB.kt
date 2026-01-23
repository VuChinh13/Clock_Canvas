package com.example.firstapp.testdoublefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firstapp.databinding.FragmentBBinding

class FragmentB : Fragment() {
    private lateinit var binding: FragmentBBinding
    private var sum = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClick.setOnClickListener {
            (activity as? DoubleFragmentMainActivity)?.updateValue(sum - 3)
        }
    }

    fun updateSum(value: Int) {
        binding.tvSum.text = value.toString()
        sum = value
    }
}