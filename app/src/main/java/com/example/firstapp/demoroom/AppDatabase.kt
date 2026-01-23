package com.example.firstapp.demoroom

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//@RenameColumn(tableName = "notes", fromColumnName = "listTest", toColumnName = "description")
//class MyAutoMigration : AutoMigrationSpec

// .fallbackToDestructiveMigration()
@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = true
    //autoMigrations = [AutoMigration(from = 1, to = 2)]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

