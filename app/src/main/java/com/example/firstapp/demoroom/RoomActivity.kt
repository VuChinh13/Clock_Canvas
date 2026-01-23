package com.example.firstapp.demoroom

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.firstapp.databinding.ActivityRoomBinding
import kotlinx.coroutines.launch

class RoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        demoInsert()
        demoGetAllData()
        demoDelete(9)
        demoGetById(1)
        demoDeleteAll()
    }

    fun demoInsert() {
        binding.btInsert.setOnClickListener {
            lifecycleScope.launch {
                for (i in 0 until 10) {
                    val note = NoteEntity(
                        title = "title $i",
                        content = "Đây là nội dung $i",
                        listTest = listOf("$i", "$i", "$i", "$i")
                    )
                    AppDatabase.getInstance(this@RoomActivity)
                        .noteDao().insert(note)
                }
            }
        }
    }

    fun demoGetAllData() {
        binding.btGetAll.setOnClickListener {
            lifecycleScope.launch {
                val notes = AppDatabase.getInstance(this@RoomActivity).noteDao().getAll()
                notes.forEach {
                    Log.d("Check", "${it.title} ,${it.content} ,${it.listTest}")
                }
            }
        }
    }

    fun demoDelete(id: Int) {
        binding.btDeleteById.setOnClickListener {
            lifecycleScope.launch {
                AppDatabase.getInstance(this@RoomActivity).noteDao().deleteById(id)
            }
        }
    }

    fun demoGetById(id: Int) {
        binding.btGetById.setOnClickListener {
            lifecycleScope.launch {
                val note = AppDatabase.getInstance(this@RoomActivity).noteDao().getNoteById(id)
                Log.d("Check", note.toString())
            }
        }
    }

    fun demoDeleteAll() {
        binding.btDeleteAll.setOnClickListener {
            lifecycleScope.launch {
                AppDatabase.getInstance(this@RoomActivity).noteDao().deleteAll()
            }
        }
    }
}