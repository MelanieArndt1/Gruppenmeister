package com.example.gruppenmeister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.gruppenmeister.databinding.FragmentNewGroupSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewGroupSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewGroupSheetBinding
    private lateinit var groupViewModel: GroupViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity= requireActivity()
        groupViewModel = ViewModelProvider(activity).get(GroupViewModel::class.java)
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
        groupViewModel.groupName.value = binding.groupName.text.toString()
        binding.groupName.setText("")
        dismiss()
    }
}