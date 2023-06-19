package com.example.gruppenmeister.todos

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.gruppenmeister.GroupMasterApplication
import com.example.gruppenmeister.MainActivity
import com.example.gruppenmeister.R
import com.example.gruppenmeister.databinding.FragmentTodoDetailBinding
import org.threeten.bp.format.DateTimeFormatter
import java.util.Calendar

class ToDoDetail(var taskItem: TaskItem) : DialogFragment() {
    private lateinit var binding: FragmentTodoDetailBinding
    private val taskViewModel: TaskViewModel by viewModels {
        val activity= requireActivity()
        TaskItemModelFactory((activity.application as GroupMasterApplication).taskRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(taskItem != null) {
            val editable = Editable.Factory.getInstance()
            binding.Name.text = editable.newEditable("Name: \n"+ taskItem!!.taskName)
            binding.Beschreibung.text = editable.newEditable("Beschreibung: \n"+ taskItem!!.taskDesc)
            binding.Prio.text = editable.newEditable("Prio: \n"+ taskItem!!.taskPrio.toString())
            binding.Due.text = editable.newEditable("Fälligkeit am: "+ DateTimeFormatter.ofPattern("dd/MMMM/yy").format(taskItem!!.taskDue()))
            if(taskItem!!.taskDue() != null)
            {
                val year = taskItem!!.taskDue()!!.year
                val monthOfYear = taskItem!!.taskDue()!!.monthValue
                val day = taskItem!!.taskDue()!!.dayOfMonth
                binding.calendarView.date = Calendar.getInstance().apply { set(year, monthOfYear, day)}.timeInMillis
            }
        }
        binding.moreIcon.setOnClickListener{
            moreAction()
        }

        binding.backAction.setOnClickListener{
            backAction()
        }
    }

    private fun moreAction() {
        val popup = PopupMenu(requireContext(), binding.moreIcon)
        popup.menuInflater.inflate(R.menu.more_item_actions_menu ,popup.menu)
        popup.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_delete -> {
                    backAction()
                    taskViewModel.deleteTask(taskItem)
                    true
                }
                R.id.action_update -> {
                    NewTaskSheet(taskItem).show(requireActivity().supportFragmentManager, "newTaskTag")
                    backAction()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun backAction() {
        val fragment = Tasks()
        (requireActivity() as MainActivity).replaceFragment(fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}