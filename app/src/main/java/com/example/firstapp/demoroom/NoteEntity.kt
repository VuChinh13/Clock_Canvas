package com.example.firstapp.demoroom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val listTest: List<String>
//    @ColumnInfo(name = "test1", defaultValue = "Chưa có dữ liệu")
//    val test1: String
)