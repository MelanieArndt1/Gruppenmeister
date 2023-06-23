package com.example.gruppenmeister.todos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gruppenmeister.databinding.TaskItemCellBinding

// Die Klasse TaskAdapter ist eine RecyclerView.Adapter-Klasse, die TaskViewHolder-Objekte verwendet.
class TaskAdapter(
    private val taskItems: List<TaskItem>, // Eine Liste von TaskItem-Objekten.
    private val clickListener: TaskItemClickListener // Ein ClickListener-Objekt.
) : RecyclerView.Adapter<TaskViewHolder>() {

    // Die Methode onCreateViewHolder() wird verwendet, um eine neue Instanz von TaskViewHolder zu erstellen
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TaskItemCellBinding.inflate(from, parent, false)
        return TaskViewHolder(parent.context, binding, clickListener)
    }

    // Die Methode getItemCount() gibt die Anzahl der Elemente in der Liste zurück
    override fun getItemCount(): Int = taskItems.size

    // Die onBindViewHolder-Methode wird aufgerufen, wenn ein ViewHolder an eine bestimmte Position gebunden wird.
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bindTaskItem(taskItems[position]) // Bindet das TaskItem an den ViewHolder.
    }
}
