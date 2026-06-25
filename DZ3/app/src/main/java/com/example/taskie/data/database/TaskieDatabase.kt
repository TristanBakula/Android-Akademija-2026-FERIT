package com.example.taskie.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskie.data.database.entities.TaskEntity


@Database(entities = [TaskEntity::class], version = 2, exportSchema = false)
abstract class TaskieDatabase : RoomDatabase() {

    abstract fun taskDao() : TaskDao

    companion object {

        @Volatile
        private var INSTANCE: TaskieDatabase? = null

        fun getDatabase(context: Context): TaskieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskieDatabase::class.java,
                    "taskie_database",
                    ).fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}