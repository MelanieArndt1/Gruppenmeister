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

    // Funktion zum Setzen des Sortier-Buttons und Sortieren der Tasksliste anhand des Namens und des Datums der Tasks, sowie Filtern nach Priorisierung
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Variablen für das Sortieren der Tasksliste werden initialisiert
        var toBeSorted = true
        var isSorted = false

        // OnClickListener für den Sortier-Button
        binding.alphaSort.setOnClickListener {
            taskViewModel.tasks.observe(viewLifecycleOwner) { original ->
                var origin = original
                var list = original.toMutableList()

                // Sortieren der Tasksliste anhand des Namens der Tasks (aufsteigend), wenn die Liste noch nicht sortiert ist
                // Darstellung des Buttons wird geändert, um Aktivierung sichtbar zu machen
                if(isSorted == false && toBeSorted == true) {
                    list.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.taskName }))
                    list.toList()
                    taskViewModel.showTasks.value = list
                    isSorted = true
                    binding.alphaSort.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    binding.alphaSort.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))

                    // Sortieren der Tasksliste anhand des Namens der Tasks (absteigend), wenn die Liste schon (aufsteigend) sortiert ist
                    // Darstellung des Buttons bleibt geändert, um Aktivierung sichtbar zu machen
                }else if(isSorted == true && toBeSorted == true){
                    list.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.taskName }))
                    list.reverse()
                    list.toList()
                    taskViewModel.showTasks.value = list
                    isSorted = false
                    toBeSorted = false
                    binding.alphaSort.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    binding.alphaSort.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))

                    // Zurücksetzen der Tasksliste auf die ursprüngliche Reihenfolge
                    // Darstellungs des Buttons wird auf ursprüngliche Darstellung zurückgesetzt, um Deaktivierung sichtbar zu machen
                }else if(toBeSorted == false){
                    taskViewModel.showTasks.value = origin
                    toBeSorted = true
                    binding.alphaSort.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
                    binding.alphaSort.setBackgroundColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
                }
            }
            updateRecyclerView()
        }

        // Variablen für das Sortieren der Tasksliste werden initialisiert
        var isDateSorted = false
        var toBeSortedDate = true
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // OnClickListener für den Sortier-Button anhand des Datums der Tasks
        binding.dateSort.setOnClickListener {
            taskViewModel.tasks.observe(viewLifecycleOwner) { original ->
                var origin = original
                var list = original.toMutableList()

                // Sortieren der Tasksliste anhand des Datums der Tasks (aufsteigend), wenn die Liste noch nicht sortiert ist
                // Darstellung des Buttons wird geändert, um Aktivierung sichtbar zu machen
                if(isDateSorted == false && toBeSortedDate == true) {
                    list.sortBy {dateFormat.parse(it.taskDueString)}
                    list.toList()
                    taskViewModel.showTasks.value = list
                    isDateSorted = true
                    binding.dateSort.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    binding.dateSort.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))

                    // Sortieren der Tasksliste anhand des Datums der Tasks (absteigend), wenn die Liste schon (aufsteigend) sortiert ist
                    // Darstellung des Buttons bleibt geändert, um Aktivierung sichtbar zu machen
                }else if(isDateSorted == true && toBeSorted == true){
                    list.sortBy {dateFormat.parse(it.taskDueString)}
                    list.reverse()
                    list.toList()
                    taskViewModel.showTasks.value = list
                    isDateSorted = false
                    toBeSortedDate = false
                    binding.dateSort.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    binding.dateSort.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))

                    // Zurücksetzen der Tasksliste auf die ursprüngliche Reihenfolge, um die Liste neu sortieren zu können
                    // Darstellungs des Buttons wird auf ursprüngliche Darstellung zurückgesetzt, um Deaktivierung sichtbar zu machen
                }else if(toBeSortedDate == false){
                    taskViewModel.showTasks.value = origin
                    toBeSortedDate = true
                    binding.dateSort.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
                    binding.dateSort.setBackgroundColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.mtrl_btn_transparent_bg_color))

                    // Zurücksetzen der Tasksliste auf die ursprüngliche Reihenfolge
                    // Darstellungs des Buttons wird auf ursprüngliche Darstellung zurückgesetzt, um Deaktivierung sichtbar zu machen
                }else{
                    taskViewModel.showTasks.value = origin
                    toBeSorted = true
                    isDateSorted = false
                    binding.dateSort.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
                    binding.dateSort.setBackgroundColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
                }
            }
            updateRecyclerView()
        }

        // Variable für das Filtern der Tasksliste wird initialisiert
        var isFiltered = false

        // OnClickListener für den Filter-Button anhand der Priorität der Tasks (nur Priorität 1)
        binding.prioFilter.setOnClickListener{
            taskViewModel.tasks.observe(viewLifecycleOwner) { original ->
                var list = original.toMutableList()
                var copy = original

                // Filtern der Tasksliste nach Priorität 1 (höchste Priorität)
                // Darstellung des Buttons wird geändert, um Aktivierung sichtbar zu machen
                if(isFiltered == false){
                    var filteredList = list.filter {it.taskPrio == 1}
                    filteredList.toList()
                    taskViewModel.showTasks.value = filteredList
                    isFiltered = true
                    binding.prioFilter.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    binding.prioFilter.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))

                    // Zurücksetzen der Tasksliste auf ohne Verwendung des Filters
                    // Darstellungs des Buttons wird auf ursprüngliche Darstellung zurückgesetzt, um Deaktivierung sichtbar zu machen
                }else{
                    taskViewModel.showTasks.value = list
                    isFiltered = false
                    binding.prioFilter.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
                    binding.prioFilter.setBackgroundColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
                }

            }
            updateRecyclerView()
        }

        // Der OnClickListener für den Button "newTaskButton" wird festgelegt und die Methode setRecyclerView() wird aufgerufen
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

    // Funktion zum Aktualisieren des RecyclerViews mit den angezeigten Tasks (wird beim Sortieren und Filtern aufgerufen)
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