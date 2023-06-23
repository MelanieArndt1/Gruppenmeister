package com.example.gruppenmeister.todos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


//Viewmodel-Klasse mit Funktionen, die die Objekte der App an das Repository übergeben
class TaskViewModel(private val repository: TaskRepository): ViewModel() {

    // LiveData-Objekt für die Liste aller Tasks
    var tasks: LiveData<List<TaskItem>> = repository.allTaskItems.asLiveData()

    // MutableLiveData-Objekt für die Liste der anzuzeigenden Tasks
    var showTasks: MutableLiveData<List<TaskItem>> = MutableLiveData<List<TaskItem>>()

    // Funktion zum Hinzufügen einer neuen Task in die Datenbank
    fun addTask(newTask: TaskItem) = viewModelScope.launch {
        repository.insertTaskItem(newTask)
    }

    // Funktion zum Aktualisieren einer Task in der Datenbank
    fun updateGruppe(TaskItem: TaskItem) = viewModelScope.launch {
        repository.updateTaskItem(TaskItem)
    }

    // Funktion zum Löschen einer Task aus der Datenbank
    fun deleteTask(TaskItem: TaskItem) = viewModelScope.launch {
        repository.deleteTaskItem(TaskItem)
    }

    // Funktion zum Ändern des Status einer Task (erledigt/nicht erledigt)
    fun setCompleted(taskItem: TaskItem) = viewModelScope.launch {
        taskItem.isCompleted = !taskItem.isCompleted
        repository.updateTaskItem(taskItem)
    }
}
// ViewModelProvider.Factory-Klasse für die Erstellung des TaskViewModel-Objekts
class TaskItemModelFactory(private val repository: TaskRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskViewModel::class.java))
            return TaskViewModel(repository) as T

        throw IllegalArgumentException("Unkown Class for View Model")
    }
}
