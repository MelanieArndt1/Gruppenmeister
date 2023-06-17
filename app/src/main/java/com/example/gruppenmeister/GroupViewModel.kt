package com.example.gruppenmeister

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.UUID

class GroupViewModel: ViewModel() {
    var gruppen = MutableLiveData<MutableList<Gruppe>>();

    init {
        gruppen.value = mutableListOf()
    }
    fun addGruppe(newGroup: Gruppe){
        val list = gruppen.value
        list!!.add(newGroup)
        gruppen.postValue(list)
    }

    fun updateGruppe(id: UUID, name: String){
        val list = gruppen.value
        val gruppe = list!!.find{it.gruppenid == id}!!
        gruppe.groupName = name
        gruppen.postValue(list)
    }
}