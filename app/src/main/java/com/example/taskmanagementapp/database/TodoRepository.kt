package com.example.taskmanagementapp.database

import com.example.taskmanagementapp.database.entity.Todo

class TodoRepository (
    private val db :TodoDatabase
){
    suspend fun insert(todo:Todo) = db.getTodoDao().insert(todo)
    suspend fun delete(todo:Todo) = db.getTodoDao().delete(todo)

    fun getAllTodoItems():List<Todo> = db.getTodoDao().getAllTodoItems()

    suspend fun update(title:String, description:String, id:Int) = db.getTodoDao().update(title, description, id)

}