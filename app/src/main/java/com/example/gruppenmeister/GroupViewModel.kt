package com.example.gruppenmeister

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GroupViewModel: ViewModel() {
    var groupName = MutableLiveData<String>()
}