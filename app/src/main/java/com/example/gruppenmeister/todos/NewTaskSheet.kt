package com.example.gruppenmeister.todos

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.gruppenmeister.GroupMasterApplication
import com.example.gruppenmeister.R
import com.example.gruppenmeister.databinding.FragmentNewTaskSheetBinding
import com.google.android.material.switchmaterial.SwitchMaterial
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter


// Die Klasse NewTaskSheet erbt von DialogFragment
class NewTaskSheet(var taskItem: TaskItem?): DialogFragment() {
    // Das binding-Objekt wird verwendet, um auf die Ansichten in der Layout-Datei zuzugreifen
    private lateinit var binding: FragmentNewTaskSheetBinding

    // Das taskViewModel-Objekt wird verwendet, um auf die Datenbank zuzugreifen
    private val taskViewModel: TaskViewModel by viewModels {
        val activity= requireActivity()
        TaskItemModelFactory((activity.application as GroupMasterApplication).taskRepository)
    }

    // Variablen für Anlegen einer Task werden initialisiert
    private var dueDate: LocalDate? = null
    private var prio: Int = 0
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMMM/yy")

    // Diese Methode wird aufgerufen, sobald die View erstellt wurde.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Wenn eine Task vorhanden ist, wird der Titel auf "Aufgabe bearbeiten" gesetzt und die Felder werden mit den Werten der Task gefüllt.
        if(taskItem != null) {
            binding.title.text = "Aufgabe bearbeiten"
            val editable = Editable.Factory.getInstance()
            binding.taskName.text = editable.newEditable(taskItem!!.taskName)
            binding.taskDesc.text = editable.newEditable(taskItem!!.taskDesc)
            prio = taskItem!!.taskPrio
            binding.prioSwitch.isChecked = taskItem!!.taskPrio == 1
            if(taskItem!!.taskDue() != null)
            {
                dueDate = taskItem!!.taskDue()!!
                updateDateButtonText()
            }
        }

        // Wenn keine Task vorhanden ist, wird der Titel auf "Neue Aufgabe" gesetzt.
        else {
            binding.title.text = "Neue Aufgabe"
        }

        // Der Button zum Öffnen des DatePickers wird initialisiert.
        binding.datePickerButton.setOnClickListener{
            openDatePicker()
        }

        // OnCheckedChangeListener für die Switch-Material-Komponente
        binding.prioSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            run {
                if (isChecked) {
                    prio = 1
                } else {
                    prio = 0
                }
            }
        }

        // OnClickListener für den Speicher-Button
        binding.taskSpeichernButton.setOnClickListener{
            saveAction()
        }
    }

    // Diese Methode öffnet den DatePicker und aktualisiert das Datum.
    private fun openDatePicker() {
        if(dueDate == null)
            dueDate = LocalDate.now()
        val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            dueDate = LocalDate.of(year, month, dayOfMonth)
            updateDateButtonText()
        }
        val dialog = DatePickerDialog(requireActivity(), listener, dueDate!!.year, dueDate!!.monthValue, dueDate!!.dayOfMonth)
        dialog.setTitle("Fälligkeit")
        dialog.show()
    }

    // Diese Methode aktualisiert den Text des DatePickers.
    private fun updateDateButtonText() {
        binding.datePickerButton.text = dateFormatter.format(dueDate)
    }

    // Diese Methode erstellt die View.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Diese Methode speichert die Task.
    private fun saveAction() {
        val taskName = binding.taskName.text.toString()
        val taskDesc = binding.taskDesc.text.toString()
        val taskDueString = if(dueDate == null) null else TaskItem.dateFormatter.format(dueDate)

        // Wenn keine Task vorhanden ist, wird eine neue Task erstellt.
        if(taskItem == null)
        {
           val newGroupItem = TaskItem( taskName, taskDesc, taskDueString, prio)
            taskViewModel.addTask(newGroupItem)
        }

        // Wenn eine Task vorhanden ist, wird die bestehende Task aktualisiert.
        else {
            taskItem!!.taskName = taskName
            taskItem!!.taskDesc = taskDesc
            taskItem!!.taskDueString = taskDueString
            taskItem!!.taskPrio = prio
            taskViewModel.updateGruppe(taskItem!!)
        }

        // Die Felder werden geleert und das DialogFragment wird geschlossen.
        binding.taskName.setText("")
        binding.taskDesc.setText("")
        binding.prioSwitch.isChecked = false
        binding.datePickerButton.setText("")
        dismiss()
    }
}
