package com.example.taskmanagementapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "todo")
data class Todo(
    var title: String?,
    var description: String?,
    //var dueDate: Date
){
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
}
