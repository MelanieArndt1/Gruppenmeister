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


class NewTaskSheet(var taskItem: TaskItem?): DialogFragment() {
    private lateinit var binding: FragmentNewTaskSheetBinding
    private val taskViewModel: TaskViewModel by viewModels {
        val activity= requireActivity()
        TaskItemModelFactory((activity.application as GroupMasterApplication).taskRepository)
    }
    private var dueDate: LocalDate? = null
    private var prio: Int = 0
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMMM/yy")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        else {
            binding.title.text = "Neue Aufgabe"
        }
        binding.datePickerButton.setOnClickListener{
            openDatePicker()
        }

        binding.prioSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            run {
                if (isChecked) {
                    prio = 1
                } else {
                    prio = 0
                }
            }
        }
        binding.taskSpeichernButton.setOnClickListener{
            saveAction()
        }
    }


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

    private fun updateDateButtonText() {
        binding.datePickerButton.text = dateFormatter.format(dueDate)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun saveAction() {
        val taskName = binding.taskName.text.toString()
        val taskDesc = binding.taskDesc.text.toString()
        val taskDueString = if(dueDate == null) null else TaskItem.dateFormatter.format(dueDate)
        if(taskItem == null)
        {
           val newGroupItem = TaskItem( taskName, taskDesc, taskDueString, prio)
            taskViewModel.addTask(newGroupItem)
        }
        else {
            taskItem!!.taskName = taskName
            taskItem!!.taskDesc = taskDesc
            taskItem!!.taskDueString = taskDueString
            taskItem!!.taskPrio = prio
            taskViewModel.updateGruppe(taskItem!!)
        }
        binding.taskName.setText("")
        binding.taskDesc.setText("")
        binding.prioSwitch.isChecked = false
        binding.datePickerButton.setText("")
        dismiss()
    }
    }
