package com.example.gruppenmeister.groups

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gruppenmeister.GroupMasterApplication
import com.example.gruppenmeister.R
import com.example.gruppenmeister.databinding.FragmentGroupsBinding

class Groups : Fragment(), GroupItemClickListener {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentGroupsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GroupAdapter

    private val groupViewModel: GroupViewModel by viewModels {
        val activity= requireActivity()
        GroupItemModelFactory((activity.application as GroupMasterApplication).groupRepository)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var isSorted = false
        binding.alphaSort.setOnClickListener {
            groupViewModel.gruppen.observe(viewLifecycleOwner) { original ->
                var list = original.toMutableList()
                if(isSorted == false) {
                    list.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.groupName }))
                    list.toList()
                    groupViewModel.showGruppen.value = list
                    isSorted = true
                }else{
                    list.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.groupName }))
                    list.reverse()
                    list.toList()
                    groupViewModel.showGruppen.value = list
                    isSorted = false
                }
            }
            updateRecyclerView()
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
            adapter = GroupAdapter(it, this@Groups)
        }
        }
    }

    fun updateRecyclerView(){
        val activity= requireActivity()
        groupViewModel.showGruppen.observe(viewLifecycleOwner){
            binding.groupListRecyclerView.apply{
                layoutManager = LinearLayoutManager(activity.applicationContext)
                adapter = GroupAdapter(it, this@Groups)
            }
        }
    }

    override fun editGroupItem(groupItem: GroupItem) {
        NewGroupSheet(groupItem).show(childFragmentManager,"newGroupTag")
    }

    override fun moreAction(groupItem: GroupItem, view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.menuInflater.inflate(R.menu.more_item_actions_menu ,popup.menu)
        popup.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_delete -> {
                    groupViewModel.deleteGroup(groupItem)
                    true
                }
                R.id.action_update -> {
                    NewGroupSheet(groupItem).show(childFragmentManager, "newGroupTag")
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
}