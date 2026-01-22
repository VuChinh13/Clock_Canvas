package com.example.firstapp.testfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.firstapp.databinding.FragmentSecondaryBinding

class SecondaryFragment : Fragment() {
    private var valueFirst = 0
    private var valueSecondary = 0
    private var result = 0
    private lateinit var binding: FragmentSecondaryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExtra(arguments)

        binding.btSum.setOnClickListener {
            result = valueSecondary + valueFirst
            binding.tvResult.text = result.toString()
        }

        binding.btMinus.setOnClickListener {
            result = valueSecondary - valueFirst
            binding.tvResult.text = result.toString()
        }

        binding.btSwitchFragment.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("extra_result", result)
            }
            parentFragmentManager.setFragmentResult("request_key", bundle)
            parentFragmentManager.popBackStack()
        }
    }

    private fun getExtra(bundle: Bundle?) {
        val user = bundle?.getParcelable("extra_user", User::class.java)
        valueFirst = bundle?.getInt("extra_value_first", 0) ?: 0
        valueSecondary = bundle?.getInt("extra_value_secondary", 0) ?: 0

        binding.tvName.text = user?.name
        binding.tvAge.text = user?.age.toString()
        binding.tvValueFirst.text = valueFirst.toString()
        binding.tvValueSecondary.text = valueSecondary.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondaryBinding.inflate(inflater, container, false)
        return binding.root
    }
}