package com.example.firstapp.testmutilanguage

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityTestMultiLanguageBinding

class TestMultiLanguageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestMultiLanguageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestMultiLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initAppLanguage()

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
                    this@TestMultiLanguageActivity,
                    R.string.login_success,
                    Toast.LENGTH_SHORT
                ).show()
            }

            btnLogout.setOnClickListener {
                Toast.makeText(
                    this@TestMultiLanguageActivity,
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


//    fun initAppLanguage() {
//        val currentLocales = AppCompatDelegate.getApplicationLocales()
//        if (!currentLocales.isEmpty) return
//
//        val localeManager = getSystemService(LocaleManager::class.java)
//        val systemLocales = localeManager.systemLocales
//
//        AppCompatDelegate.setApplicationLocales(
//            LocaleListCompat.wrap(systemLocales)
//        )
//    }


//    fun initAppLanguage() {
//        val currentLocales = AppCompatDelegate.getApplicationLocales()
//        if (!currentLocales.isEmpty) return
//        val supported = setOf("en", "vi", "ja")
//        val systemLang = Locale.getDefault().language
//        val targetLang = if (systemLang in supported) { systemLang } else { "ja" }
//        AppCompatDelegate.setApplicationLocales( LocaleListCompat.forLanguageTags(targetLang) )
//
//    }
}