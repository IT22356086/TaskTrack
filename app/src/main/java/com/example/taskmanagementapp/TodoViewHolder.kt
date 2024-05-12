package com.example.taskmanagementapp

import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class TodoViewHolder(view: View):ViewHolder(view) {
    val title:TextView = view.findViewById(R.id.tvtitle)
    val description:TextView = view.findViewById(R.id.tvdescription)

    val ibDelete:ImageButton = view.findViewById(R.id.btnDelete)
}