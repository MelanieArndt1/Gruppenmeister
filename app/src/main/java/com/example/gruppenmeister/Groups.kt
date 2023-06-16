package com.example.gruppenmeister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.gruppenmeister.databinding.FragmentGroupsBinding

class Groups : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentGroupsBinding
    private lateinit var groupViewModel: GroupViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity= requireActivity()
        groupViewModel = ViewModelProvider(activity).get(GroupViewModel::class.java)
        binding.newGroupButton.setOnClickListener{
                val newGroupSheet = NewGroupSheet()
            newGroupSheet.show(childFragmentManager,"newGroupTag")
            }
        groupViewModel.groupName.observe(viewLifecycleOwner){
            binding.groupName.text = String.format("Gruppen Name: %s", it)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupsBinding.inflate(inflater, container, false)
        return binding.root
    }
}