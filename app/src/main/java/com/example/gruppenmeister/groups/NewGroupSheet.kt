package com.example.gruppenmeister.groups

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.gruppenmeister.GroupMasterApplication
import com.example.gruppenmeister.databinding.FragmentNewGroupSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewGroupSheet(var groupItem: GroupItem? ) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewGroupSheetBinding
    private val groupViewModel: GroupViewModel by viewModels {
        val activity= requireActivity()
        GroupItemModelFactory((activity.application as GroupMasterApplication).groupRepository)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(groupItem != null) {
            binding.title.text = "Gruppe bearbeiten"
            val editable = Editable.Factory.getInstance()
            binding.groupName.text = editable.newEditable(groupItem!!.groupName)
        }
        else {
            binding.title.text = "Neue Gruppe"
        }

       // groupViewModel = ViewModelProvider(activity).get(GroupViewModel::class.java)
        binding.gruppeSpeichernButton.setOnClickListener{
            saveAction()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewGroupSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun saveAction() {
        val groupName = binding.groupName.text.toString()
        if(groupItem == null)
        {
            val newGroupItem = GroupItem( groupName)
            groupViewModel.addGroup(newGroupItem)
        }
        else {
            groupItem!!.groupName = groupName
            groupViewModel.updateGruppe(groupItem!!)
        }
        binding.groupName.setText("")
        dismiss()
    }
}