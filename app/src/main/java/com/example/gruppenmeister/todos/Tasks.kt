package com.example.gruppenmeister.todos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gruppenmeister.GroupMasterApplication
import com.example.gruppenmeister.databinding.FragmentToDosBinding
import java.text.SimpleDateFormat
import java.util.Locale

class Tasks : Fragment(), TaskItemClickListener {

    private lateinit var binding: FragmentToDosBinding
    private val taskViewModel: TaskViewModel by viewModels {
        val activity= requireActivity()
        TaskItemModelFactory((activity.application as GroupMasterApplication).taskRepository)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //var origin = List<TaskItem>(8,3)
        var start = true
        var isSorted = false
        binding.alphaSort.setOnClickListener {
            taskViewModel.tasks.observe(viewLifecycleOwner) { original ->
                var origin = original
                var list = original.toMutableList()
                if(isSorted == false && start == true) {
                    list.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.taskName }))
                    list.toList()
                    taskViewModel.showTasks.value = list
                    isSorted = true
                }else if(isSorted == true && start == true){
                    list.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.taskName }))
                    list.reverse()
                    list.toList()
                    taskViewModel.showTasks.value = list
                    isSorted = false
                    start = false
                }else if(start == false){
                    taskViewModel.showTasks.value = origin
                    start = true
                }
            }
            updateRecyclerView()
        }

        var isDateSorted = false
        var startDate = true
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        binding.dateSort.setOnClickListener {
            taskViewModel.tasks.observe(viewLifecycleOwner) { original ->
                var origin = original
                var list = original.toMutableList()
                if(isDateSorted == false && startDate == true) {
                    list.sortBy {dateFormat.parse(it.taskDueString)}
                    list.toList()
                    taskViewModel.showTasks.value = list
                    isDateSorted = true
                }else if(isDateSorted == true && start == true){
                    list.sortBy {dateFormat.parse(it.taskDueString)}
                    list.reverse()
                    list.toList()
                    taskViewModel.showTasks.value = list
                    isDateSorted = false
                    startDate = false
                }else if(startDate == false){
                    taskViewModel.showTasks.value = origin
                    startDate = true
                }else{
                    taskViewModel.showTasks.value = origin
                    start = true
                    isDateSorted = false
                }
            }
            updateRecyclerView()
        }

        var isFiltered = false
        binding.prioFilter.setOnClickListener{
            taskViewModel.tasks.observe(viewLifecycleOwner) { original ->
                var list = original.toMutableList()
                var copy = original
                if(isFiltered == false){
                    var filteredList = list.filter {it.taskPrio == 1}
                    filteredList.toList()
                    taskViewModel.showTasks.value = filteredList
                    isFiltered = true
                }else{
                    taskViewModel.showTasks.value = list
                    isFiltered = false
                }

            }
            updateRecyclerView()
        }

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

    fun updateRecyclerView(){
        val activity= requireActivity()
        taskViewModel.showTasks.observe(viewLifecycleOwner){
            binding.taskListRecyclerView.apply{
                layoutManager = LinearLayoutManager(activity.applicationContext)
                adapter = TaskAdapter(it, this@Tasks)
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(childFragmentManager, "newTaskTag")
    }

    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem);
    }
}