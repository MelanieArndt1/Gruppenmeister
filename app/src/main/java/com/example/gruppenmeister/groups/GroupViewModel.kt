package com.example.gruppenmeister.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class GroupViewModel(private val repository: GroupRepository): ViewModel() {
    var gruppen: LiveData<List<GroupItem>> = repository.allGroupItems.asLiveData()
    fun addGroup(newGroup: GroupItem) = viewModelScope.launch {
        repository.insertGroupItem(newGroup)
    }

    fun updateGruppe(groupItem: GroupItem) = viewModelScope.launch {
        repository.updateGroupItem(groupItem)
    }
}

class GroupItemModelFactory(private val repository: GroupRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GroupViewModel::class.java))
            return GroupViewModel(repository) as T

        throw IllegalArgumentException("Unkown Class for View Model")
    }
}