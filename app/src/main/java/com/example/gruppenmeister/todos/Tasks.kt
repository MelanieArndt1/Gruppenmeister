package com.example.gruppenmeister.todos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gruppenmeister.GroupMasterApplication
import com.example.gruppenmeister.MainActivity
import com.example.gruppenmeister.R
import com.example.gruppenmeister.databinding.FragmentToDosBinding

class Tasks : Fragment(), TaskItemClickListener {

    private lateinit var binding: FragmentToDosBinding
    private val taskViewModel: TaskViewModel by viewModels {
        val activity= requireActivity()
        TaskItemModelFactory((activity.application as GroupMasterApplication).taskRepository)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newTaskButton.setOnClickListener{
            val newGroupSheet = NewTaskSheet(null)
            newGroupSheet.show(childFragmentManager,"newGroupTag")
        }
        setRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToDosBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun setRecyclerView(){
        val activity= requireActivity()
        taskViewModel.tasks.observe(viewLifecycleOwner){
            binding.taskListRecyclerView.apply{
                layoutManager = LinearLayoutManager(activity.applicationContext)
                adapter = TaskAdapter(it, this@Tasks)
            }
        }
    }
    override fun showTaskItemDetails(taskItem: TaskItem) {
        val fragment = ToDoDetail(taskItem)
        (requireActivity() as MainActivity).replaceFragment(fragment)
        //NewTaskSheet(taskItem).show(childFragmentManager, "newTaskTag")
    }

    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem);
    }
}