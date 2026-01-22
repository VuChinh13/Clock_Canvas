package com.example.firstapp.testfragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityTestFragmentBinding

class TestFragmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        switchFirstFragment()
    }

    private fun switchFirstFragment() {
        val firstFragment = FirstFragment()
        val transactionHomeMusicFragment = supportFragmentManager.beginTransaction()
        transactionHomeMusicFragment.add(R.id.fragmentMain, firstFragment)
        transactionHomeMusicFragment.commit()
    }

    fun switchSecondaryFragment(user: User, valueFirst: Int, valueSecondary: Int) {
        val secondaryFragment = SecondaryFragment().apply {
            arguments = Bundle().apply {
                putParcelable("extra_user", user)
                putInt("extra_value_first", valueFirst)
                putInt("extra_value_secondary", valueSecondary)
            }
        }
        val transactionHomeMusicFragment = supportFragmentManager.beginTransaction()
        transactionHomeMusicFragment.add(R.id.fragmentMain, secondaryFragment)
        transactionHomeMusicFragment.addToBackStack(null)
        transactionHomeMusicFragment.commit()
    }
}