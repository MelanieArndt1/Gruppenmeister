package com.example.gruppenmeister.todos

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.gruppenmeister.databinding.TaskItemCellBinding
import org.threeten.bp.format.DateTimeFormatter

// ViewHolder-Klasse für die Tasksliste
class TaskViewHolder(
    private val context: Context,
    private val binding: TaskItemCellBinding,
    private val clickListener: TaskItemClickListener
): RecyclerView.ViewHolder(binding.root) {
    // DateTimeFormatter-Objekt für das Formatieren des Datums
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MMMM/yy")

    // Funktion zum Binden einer Task an die View-Elemente des ViewHolders
    fun bindTaskItem(taskItem: TaskItem){
        binding.taskName.text = taskItem.taskName

        // Wenn das Fälligkeitsdatum der Task nicht null ist, wird es formatiert und angezeigt
        if(taskItem.taskDue() != null) {
            binding.dueName.text = dateFormatter.format(taskItem.taskDue())
        }
        else
            binding.dueName.text =  ""

        // Setzen des Bildes und der Farbe des Check-Buttons basierend auf dem Status der Task
        binding.checkButton.setImageResource(taskItem.checkImageRessource())
        binding.checkButton.setColorFilter(taskItem.checkImageColor(context))

        // Setzen des Bildes und der Farbe des Prioritäts-Icons basierend auf der Priorität der Task
        binding.prioIcon.setImageResource(taskItem.prioImageRessource())
        binding.prioIcon.setColorFilter(taskItem.prioImageColor(context))

        // OnClickListener für den Container der Tasks-Zelle (zum Anzeigen von Details)
        binding.taskCellContainer.setOnClickListener{
            clickListener.showTaskItemDetails(taskItem)
        }

        // OnClickListener für den Check-Button (zum Ändern des Status der Task)
        binding.checkButton.setOnClickListener{
            clickListener.completeTaskItem(taskItem)
        }
    }
}