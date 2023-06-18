package com.example.gruppenmeister.groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Group
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gruppenmeister.GroupMasterApplication
import com.example.gruppenmeister.databinding.FragmentGroupsBinding

class Groups : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentGroupsBinding
    private val groupViewModel: GroupViewModel by viewModels {
        val activity= requireActivity()
        GroupItemModelFactory((activity?.application as GroupMasterApplication).repository)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val activity= requireActivity()
//        groupViewModel = ViewModelProvider(activity).get(GroupViewModel::class.java)


        binding.alphaSort.setOnClickListener {
            var list : MutableList<GroupItem> = mutableListOf()
            groupViewModel.gruppen.observe(viewLifecycleOwner) {
                list = (it).toMutableList()
                list.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.groupName }))
                list.toList()
            }
            val adapter = GroupAdapter(list)
            //val from_pos = RecyclerView.ViewHolder.adapterPosition
            //val to_pos = target.adapterPosition


            //adapter.notifyDataSetChanged()

            list.forEach{
                groupViewModel.deleteGruppe(it)
                groupViewModel.addGroup(it)
                //adapter.notifyItemMoved()
            }
            groupViewModel.
        }


        binding.newGroupButton.setOnClickListener{
                val newGroupSheet = NewGroupSheet(null)
            newGroupSheet.show(childFragmentManager,"newGroupTag")
        }
        setRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupsBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun setRecyclerView(){
        val activity= requireActivity()
        groupViewModel.gruppen.observe(viewLifecycleOwner){
            binding.groupListRecyclerView.apply{
                layoutManager = LinearLayoutManager(activity.applicationContext)
                adapter = GroupAdapter(it)
            }
        }
    }
}