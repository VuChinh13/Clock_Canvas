package com.example.firstapp.demoroom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import androidx.room.Update

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)

    @Query("SELECT * FROM notes")
    suspend fun getAll(): List<NoteEntity>

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteById(noteId: Int)

    @Update
    suspend fun update(note: NoteEntity)

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): NoteEntity?

    @Query("DELETE FROM notes")
    suspend fun deleteAll()
}