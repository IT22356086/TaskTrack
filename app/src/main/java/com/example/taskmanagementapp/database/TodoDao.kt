package com.example.taskmanagementapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.taskmanagementapp.database.entity.Todo

@Dao
interface TodoDao {
    @Insert
    suspend fun insert(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("SELECT * FROM todo")
    fun getAllTodoItems():List<Todo>

    @Query("SELECT * FROM todo WHERE id =:id")
    fun getOne(id:Int): Todo

    @Query("UPDATE Todo SET title = :title, description = :description WHERE id = :id")
    suspend fun update(title: String, description: String, id: Int)

    @Query()
}