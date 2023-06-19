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
        GroupItemModelFactory((activity?.application as GroupMasterApplication).repository)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val activity= requireActivity()
//        groupViewModel = ViewModelProvider(activity).get(GroupViewModel::class.java)

        //recyclerView = view.findViewById(R.id.recyclerView)
        //adapter = GroupAdapter()

        binding.alphaSort.setOnClickListener {
            var list: MutableList<GroupItem> = mutableListOf()
            val from_positionMap = mutableMapOf<GroupItem, Int>()
            val to_positionMap = mutableMapOf<GroupItem, Int>()
            groupViewModel.gruppen.observe(viewLifecycleOwner) { original ->
                var list = original.toMutableList()
                list.forEachIndexed{index, item ->
                    from_positionMap[item] = index
                }
                list.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.groupName }))
                list.toList()
                list.forEachIndexed{index, item ->
                    to_positionMap[item] = index
                }

                groupViewModel.showGruppen.value = list
                //adapter.notifyDataSetChanged()
                /*from_positionMap.forEach { (item, fromPosition) ->
                    //val toPosition = to_positionMap[item]
                    if (to_positionMap.containsKey(item)) {
                        // Änderungen an den Positionen durch notifyItemMoved reflektieren
                        adapter.notifyItemMoved(fromPosition, to_positionMap[item]!!)
                    }
                }*/
            }

            groupViewModel.showGruppen.observe(viewLifecycleOwner) { updatedList ->
                adapter = GroupAdapter(list)
                from_positionMap.forEach { (item, fromPosition) ->
                    if (to_positionMap.containsKey(item)) {
                        var to = to_positionMap[item]
                        Handler().postDelayed({
                            adapter.notifyItemMoved(fromPosition, to_positionMap[item]!!)
                        }, 100)
                    }
                }
                //adapter.notifyDataSetChanged()
                println()
            }
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
}