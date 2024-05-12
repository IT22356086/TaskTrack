package com.example.taskmanagementapp

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagementapp.database.TodoDatabase
import com.example.taskmanagementapp.database.TodoRepository
import com.example.taskmanagementapp.database.entity.Todo
import com.example.taskmanagementapp.viewmodel.MainActivityData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var repository: TodoRepository
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var viewmodel:MainActivityData
    private lateinit var recyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        recyclerView = findViewById(R.id.todo_recyclerview)
        repository = TodoRepository(TodoDatabase.getInstance(this))
        viewmodel = ViewModelProvider(this)[MainActivityData::class.java]

        viewmodel.data.observe(this){
            val todoAdapter = TodoAdapter(it, repository, viewmodel)
            recyclerView.adapter = todoAdapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAllTodoItems()

            runOnUiThread{
                viewmodel.setData(data)
            }
        }

        val addButton:ImageButton = findViewById(R.id.btnAddTask)
        addButton.setOnClickListener{
            addTask()
        }

    }

    private fun addTask(){
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.custom_alert_dialog, null)

        builder.setView(dialogView)

        // Access the EditTexts in the custom layout
        title = dialogView.findViewById<EditText>(R.id.editTextTitle)
        description = dialogView.findViewById<EditText>(R.id.editTextDescription)

        builder.setPositiveButton("Save") { dialog, which ->
            var title = title.text.toString()
            var description = description.text.toString()
            // Add your logic to handle the task addition
            CoroutineScope(Dispatchers.IO).launch {
                repository.insert(Todo(title,description))
                val data = repository.getAllTodoItems()
                runOnUiThread(){
                    viewmodel.setData(data)
                }
            }
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }
}