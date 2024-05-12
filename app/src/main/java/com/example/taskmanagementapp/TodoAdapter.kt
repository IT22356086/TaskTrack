package com.example.taskmanagementapp

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagementapp.database.TodoRepository
import com.example.taskmanagementapp.database.entity.Todo
import com.example.taskmanagementapp.viewmodel.MainActivityData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoAdapter(todoTasks: List<Todo>, repositary: TodoRepository, viewModel: MainActivityData): RecyclerView.Adapter<TodoViewHolder>() {

    private var context:Context? = null
    private var todoTasks = todoTasks
    private var repositary = repositary
    private var viewModel = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tasks_layout,parent,false)
        context = parent.context

        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todoTasks.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val task = todoTasks[position]
        holder.title.text = task.title
        holder.description.text = task.description
        holder.ibDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context?.getString(R.string.deleteAlert_title))
            builder.setMessage(context?.getString(R.string.deleteAlert_msg))
            builder.setPositiveButton(context?.getString(R.string.deleteAlert_delete)) { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    repositary.delete(task)
                    //refreshing data set after deleting a task
                    todoTasks = repositary.getAllTodoItems()

                    withContext(Dispatchers.Main) {
                        viewModel.setData(todoTasks)
                    }
                }
            }
            builder.setNegativeButton(context?.getString(R.string.deleteAlert_cancel)) { dialog, _ ->
                dialog.cancel()
            }
            val alertDialog = builder.create()
            alertDialog.show()

        }
    }

}