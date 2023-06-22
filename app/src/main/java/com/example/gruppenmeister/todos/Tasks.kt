package com.example.gruppenmeister.todos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gruppenmeister.GroupMasterApplication
import com.example.gruppenmeister.MainActivity
import com.example.gruppenmeister.R
import com.example.gruppenmeister.databinding.FragmentToDosBinding
import java.text.SimpleDateFormat
import java.util.Locale

// Fragment-Klasse für die Tasksliste
class Tasks : Fragment(), TaskItemClickListener {

    // Binding-Objekt für das Layout des Fragments
    private lateinit var binding: FragmentToDosBinding

    // ViewModel-Objekt für die Tasksliste (mit Dependency Injection des Taskrepositorys)
    private val taskViewModel: TaskViewModel by viewModels {
        val activity= requireActivity()
        TaskItemModelFactory((activity.application as GroupMasterApplication).taskRepository)
    }

    // Funktion zum Setzen des Sortier-Buttons und Sortieren der Tasksliste nach dem Namen und dem Datum der Tasks, sowie Filtern nach Prio
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Variablen für das Sortieren der Tasksliste
        var start = true
        var isSorted = false

        // OnClickListener für den Sortier-Button
        binding.alphaSort.setOnClickListener {
            taskViewModel.tasks.observe(viewLifecycleOwner) { original ->
                var origin = original
                var list = original.toMutableList()

                // Sortieren der Tasksliste nach dem Namen der Tasks (aufsteigend)
                if(isSorted == false && start == true) {
                    list.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.taskName }))
                    list.toList()
                    taskViewModel.showTasks.value = list
                    isSorted = true
                    binding.alphaSort.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    binding.alphaSort.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))

                    // Sortieren der Tasksliste nach dem Namen der Tasks (absteigend)
                }else if(isSorted == true && start == true){
                    list.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.taskName }))
                    list.reverse()
                    list.toList()
                    taskViewModel.showTasks.value = list
                    isSorted = false
                    start = false
                    binding.alphaSort.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    binding.alphaSort.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))

                    // Zurücksetzen der Tasksliste auf die ursprüngliche Reihenfolge
                }else if(start == false){
                    taskViewModel.showTasks.value = origin
                    start = true
                    binding.alphaSort.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
                    binding.alphaSort.setBackgroundColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
                }
            }
            updateRecyclerView()
        }

        // Variablen für das Sortieren der Tasksliste
        var isDateSorted = false
        var startDate = true
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // OnClickListener für den Sortier-Button nach dem Datum der Tasks
        binding.dateSort.setOnClickListener {
            taskViewModel.tasks.observe(viewLifecycleOwner) { original ->
                var origin = original
                var list = original.toMutableList()

                // Sortieren der Tasksliste nach dem Datum der Tasks (aufsteigend)
                if(isDateSorted == false && startDate == true) {
                    list.sortBy {dateFormat.parse(it.taskDueString)}
                    list.toList()
                    taskViewModel.showTasks.value = list
                    isDateSorted = true
                    binding.dateSort.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    binding.dateSort.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))

                    // Sortieren der Tasksliste nach dem Datum der Tasks (absteigend)
                }else if(isDateSorted == true && start == true){
                    list.sortBy {dateFormat.parse(it.taskDueString)}
                    list.reverse()
                    list.toList()
                    taskViewModel.showTasks.value = list
                    isDateSorted = false
                    startDate = false
                    binding.dateSort.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    binding.dateSort.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))

                    // Zurücksetzen der Tasksliste auf die ursprüngliche Reihenfolge
                }else if(startDate == false){
                    taskViewModel.showTasks.value = origin
                    startDate = true
                    binding.dateSort.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
                    binding.dateSort.setBackgroundColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.mtrl_btn_transparent_bg_color))

                    // Zurücksetzen der Tasksliste auf die ursprüngliche Reihenfolge
                }else{
                    taskViewModel.showTasks.value = origin
                    start = true
                    isDateSorted = false
                    binding.dateSort.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
                    binding.dateSort.setBackgroundColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
                }
            }
            updateRecyclerView()
        }

        // Variablen für das Filtern der Tasksliste
        var isFiltered = false

        // OnClickListener für den Filter-Button nach der Priorität der Tasks (nur Priorität 1)
        binding.prioFilter.setOnClickListener{
            taskViewModel.tasks.observe(viewLifecycleOwner) { original ->
                var list = original.toMutableList()
                var copy = original

                // Filtern der Tasksliste nach Priorität 1 (höchste Priorität)
                if(isFiltered == false){
                    var filteredList = list.filter {it.taskPrio == 1}
                    filteredList.toList()
                    taskViewModel.showTasks.value = filteredList
                    isFiltered = true
                    binding.prioFilter.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    binding.prioFilter.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))

                    // Zurücksetzen der Tasksliste auf die ursprüngliche Reihenfolge
                }else{
                    taskViewModel.showTasks.value = list
                    isFiltered = false
                    binding.prioFilter.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
                    binding.prioFilter.setBackgroundColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
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

    // Erstellen des Fragments und Inflaten des Layouts
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToDosBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Funktion zum Setzen des RecyclerViews mit allen Tasks (wird beim Starten des Fragments aufgerufen)
    fun setRecyclerView(){
        val activity= requireActivity()
        taskViewModel.tasks.observe(viewLifecycleOwner){
            binding.taskListRecyclerView.apply{
                layoutManager = LinearLayoutManager(activity.applicationContext)
                adapter = TaskAdapter(it, this@Tasks)
            }
        }
    }

    // Funktion zum Aktualisieren des RecyclerViews mit den angezeigten Tasks (wird beim Filtern aufgerufen)
    fun updateRecyclerView(){
        val activity= requireActivity()
        taskViewModel.showTasks.observe(viewLifecycleOwner){
            binding.taskListRecyclerView.apply{
                layoutManager = LinearLayoutManager(activity.applicationContext)
                adapter = TaskAdapter(it, this@Tasks)
            }
        }
    }

    // Funktion zum Anzeigen der Details einer Task (wird aufgerufen, wenn auf eine Tasks-Zelle geklickt wird)
    override fun showTaskItemDetails(taskItem: TaskItem) {
        // Erstellen eines neuen ToDoDetail-Fragments mit der ausgewählten Task und Austauschen des aktuellen Fragments durch das neue Fragment
        val fragment = ToDoDetail(taskItem)
        (requireActivity() as MainActivity).replaceFragment(fragment)
    }

    // Funktion zum Ändern des Status einer Task (wird aufgerufen, wenn auf den Check-Button einer Tasks-Zelle geklickt wird)
    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem);
    }
}