package com.example.gruppenmeister.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

// Die Klasse GroupViewModel erbt von ViewModel
class GroupViewModel(private val repository: GroupRepository): ViewModel() {

    // Die Variable gruppen enthält alle Gruppen in der Datenbank
    var gruppen: LiveData<List<GroupItem>> = repository.allGroupItems.asLiveData()

    // Die Variable showGruppen wird verwendet, um eine gefilterte Liste von Gruppen anzuzeigen
    var showGruppen: MutableLiveData<List<GroupItem>> = MutableLiveData<List<GroupItem>>()

    // Die Methode addGroup() fügt eine neue Gruppe zur Datenbank hinzu
    fun addGroup(newGroup: GroupItem) = viewModelScope.launch {
        repository.insertGroupItem(newGroup)
    }

    // Die Methode updateGruppe() aktualisiert eine vorhandene Gruppe in der Datenbank
    fun updateGruppe(groupItem: GroupItem) = viewModelScope.launch {
        repository.updateGroupItem(groupItem)
    }

    // Die Methode deleteGroup() löscht eine vorhandene Gruppe aus der Datenbank
    fun deleteGroup(groupItem: GroupItem) = viewModelScope.launch {
        repository.deleteGroupItem(groupItem)
    }
}

// Die Klasse GroupItemModelFactory implementiert die Factory-Schnittstelle von ViewModelProvider
class GroupItemModelFactory(private val repository: GroupRepository): ViewModelProvider.Factory{

    // Die Methode create() erstellt eine Instanz der Klasse GroupViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GroupViewModel::class.java))
            return GroupViewModel(repository) as T

        throw IllegalArgumentException("Unkown Class for View Model")
    }
}
