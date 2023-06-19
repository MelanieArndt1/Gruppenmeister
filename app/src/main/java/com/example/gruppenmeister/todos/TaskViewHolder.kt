package com.example.gruppenmeister.todos

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.gruppenmeister.databinding.TaskItemCellBinding
import org.threeten.bp.format.DateTimeFormatter

class TaskViewHolder(
    private val context: Context,
    private val binding: TaskItemCellBinding,
    private val clickListener: TaskItemClickListener
): RecyclerView.ViewHolder(binding.root) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MMMM/yy")
    fun bindTaskItem(taskItem: TaskItem){
        binding.taskName.text = taskItem.taskName

        //binding.prioIcon. = taskItem.prio

        if(taskItem.taskDue() != null) {
            binding.dueName.text = dateFormatter.format(taskItem.taskDue())
        }
        else
            binding.dueName.text =  ""

        binding.checkButton.setImageResource(taskItem.checkImageRessource())
        binding.prioIcon.setImageResource(taskItem.prioImageRessource())
        binding.checkButton.setColorFilter(taskItem.imageColor(context))
        binding.taskCellContainer.setOnClickListener{
            clickListener.editTaskItem(taskItem)
        }
        binding.checkButton.setOnClickListener{
            clickListener.completeTaskItem(taskItem)
        }
    }

}