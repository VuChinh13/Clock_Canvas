package com.example.firstapp.testmutilanguage

import android.os.Bundle
import android.os.LocaleList
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityTestMultiLanguageBinding
import java.util.Locale

class TestMultiLanguageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestMultiLanguageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestMultiLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAppLanguage()

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

    fun initAppLanguage() {
        val currentLocales = AppCompatDelegate.getApplicationLocales()
        if (!currentLocales.isEmpty) return
        val initialLocale = detectInitialLanguage() ?: return
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.create(initialLocale))
    }

    fun detectInitialLanguage(): Locale? {
        val locales = LocaleList.getDefault()

        for (i in 0 until locales.size()) {
            val locale = locales[i]
            when (locale.language) {
                "ja" -> return Locale.JAPAN
                "en" -> return Locale.US
                "vi" -> return Locale.forLanguageTag("vi")
            }
        }

        return null
    }

//    fun detectInitialLanguage(): Locale{
//        val systemLocales = LocaleList.getDefault()
//        val supportLocales = getSupportedLocales()
//
//        for (i in 0 until supportLocales.size){
//            val systemLocale = systemLocales[i]
//
//            supportLocales.firstOrNull {
//                it.language == systemLocale.language
//            }?.let { return it }
//        }
//
//        return supportLocales.firstOrNull() ?: Locale.getDefault()
//    }

//    fun getSupportedLocales(): List<Locale> =
//        resources.getXml(R.xml.locales_config).use { parser ->
//            generateSequence {
//                if (parser.next() != XmlPullParser.START_TAG) null
//                else if (parser.name == "locale")
//                    parser.getAttributeValue(
//                        "http://schemas.android.com/apk/res/android",
//                        "name"
//                    )
//                else null
//            }
//                .filterNotNull()
//                .map(Locale::forLanguageTag)
//                .toList()
//        }
}