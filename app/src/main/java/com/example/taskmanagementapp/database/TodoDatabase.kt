package com.example.taskmanagementapp.database

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskmanagementapp.database.entity.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase:RoomDatabase() {
    abstract fun getTodoDao():TodoDao

    companion object{
        @Volatile
        private var INSTANCE:TodoDatabase? = null

        fun getInstance(context: Context):TodoDatabase{
            synchronized(this){
                return INSTANCE?:Room.databaseBuilder(
                    context,
                    TodoDatabase::class.java,
                    "todo_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}