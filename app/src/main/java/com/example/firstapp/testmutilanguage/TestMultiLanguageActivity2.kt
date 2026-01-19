package com.example.firstapp.testmutilanguage

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityTestMultiLanguage2Binding

class TestMultiLanguageActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityTestMultiLanguage2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestMultiLanguage2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if (AppCompatDelegate.getApplicationLocales().isEmpty) {
            Log.d("Check",AppCompatDelegate.getApplicationLocales().isEmpty.toString())
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags("ja")
            )
        }

        with(binding) {
            btnVietnamese.setOnClickListener {
                setAppLanguage("vi")
            }

            btnEnglish.setOnClickListener {
                setAppLanguage("en")
            }

            btnJapan.setOnClickListener {
                setAppLanguage("ja")
            }

            btnLogin.setOnClickListener {
                Toast.makeText(
                    this@TestMultiLanguageActivity2,
                    R.string.login_success,
                    Toast.LENGTH_SHORT
                ).show()
            }

            btnLogout.setOnClickListener {
                Toast.makeText(
                    this@TestMultiLanguageActivity2,
                    R.string.error_network,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun setAppLanguage(languageCode: String) {
        val localeList = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(localeList)
    }
}