package com.example.gruppenmeister.todos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.gruppenmeister.groups.GroupItem
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


//Viewmodel Klasse mit funktionen, die die Objekte der App an das Repository übergeben
class TaskViewModel(private val repository: TaskRepository): ViewModel() {
    var tasks: LiveData<List<TaskItem>> = repository.allTaskItems.asLiveData()
    var showTasks: MutableLiveData<List<TaskItem>> = MutableLiveData<List<TaskItem>>()
    fun addTask(newTask: TaskItem) = viewModelScope.launch {
        repository.insertTaskItem(newTask)
    }

    fun updateGruppe(TaskItem: TaskItem) = viewModelScope.launch {
        repository.updateTaskItem(TaskItem)
    }

    fun deleteTask(TaskItem: TaskItem) = viewModelScope.launch {
        repository.deleteTaskItem(TaskItem)
    }

    fun setCompleted(taskItem: TaskItem) = viewModelScope.launch {
        taskItem.isCompleted = !taskItem.isCompleted
        repository.updateTaskItem(taskItem)
    }
}

class TaskItemModelFactory(private val repository: TaskRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskViewModel::class.java))
            return TaskViewModel(repository) as T

        throw IllegalArgumentException("Unkown Class for View Model")
    }
}
