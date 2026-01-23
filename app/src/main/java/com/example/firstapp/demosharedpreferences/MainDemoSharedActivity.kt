package com.example.firstapp.demosharedpreferences

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapp.databinding.ActivityMainDemoSharedBinding
import com.google.gson.Gson
import androidx.core.content.edit
import com.google.gson.reflect.TypeToken

class MainDemoSharedActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    private lateinit var binding: ActivityMainDemoSharedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainDemoSharedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("demo_prefs", MODE_PRIVATE)

        getData()
        saveData()
        removeData()
        removeAllData()

        saveListObject()
        getListObject()
    }

    fun getData() {
        with(binding) {
            binding.btGetData.setOnClickListener {
                val json = prefs.getString("value_object", null)
                val user = json?.let {
                    Gson().fromJson(it, UserData::class.java)
                }

                etValueFirst.setText(prefs.getInt("value_int", 0).toString())
                etValueSecondary.setText(prefs.getString("value_string", ""))
                etName.setText(user?.name)
                etAge.setText(user?.age.toString())
            }
        }
    }

    fun saveData() {
        with(binding) {
            btSave.setOnClickListener {
                prefs.edit {
                    putInt("value_int", etValueFirst.text.toString().toIntOrNull() ?: 0)
                        .putString("value_string", etValueSecondary.text.toString())
                }

                val user =
                    UserData(etName.text.toString(), etAge.text.toString().toIntOrNull() ?: 0)
                val json = Gson().toJson(user)
                prefs.edit { putString("value_object", json) }
            }
        }
    }

    fun removeAllData() {
        binding.btRemoveAll.setOnClickListener {
            prefs.edit { clear() }
        }
    }

    fun removeData() {
        binding.btRemove.setOnClickListener {
            prefs.edit { remove("value_int") }
        }
    }

    fun saveListObject() {
        val userList = listOf(
            UserData("A", 20),
            UserData("B", 25),
            UserData("C", 30)
        )

        val json = Gson().toJson(userList)
        prefs.edit { putString("value_user_list", json) }
    }

    fun getListObject() {
        val json = prefs.getString("value_user_list", null)

        val type = object : TypeToken<List<UserData>>() {}.type
        val users = json?.let { Gson().fromJson<List<UserData>>(it, type) }

        users?.forEach {
            Log.d("Check", "${it.name} - ${it.age}")
        }
    }
}