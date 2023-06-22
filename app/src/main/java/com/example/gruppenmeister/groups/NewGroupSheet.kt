package com.example.gruppenmeister.groups

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.gruppenmeister.GroupMasterApplication
import com.example.gruppenmeister.databinding.FragmentNewGroupSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// Die Klasse NewGroupSheet erbt von BottomSheetDialogFragment
class NewGroupSheet(var groupItem: GroupItem? ) : BottomSheetDialogFragment() {

    // Das binding-Objekt wird verwendet, um auf die Ansichten in der Layout-Datei zuzugreifen
    private lateinit var binding: FragmentNewGroupSheetBinding

    // Das groupViewModel-Objekt wird verwendet, um auf die Datenbank zuzugreifen
    private val groupViewModel: GroupViewModel by viewModels {
        val activity= requireActivity()
        GroupItemModelFactory((activity.application as GroupMasterApplication).groupRepository)
    }

    // Die Methode onViewCreated() wird aufgerufen, nachdem die Ansicht erstellt wurde
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Wenn das Feld groupItem nicht null ist, wird der Text des Titels auf "Gruppe bearbeiten" gesetzt und der Name der Gruppe wird in das Textfeld "groupName" eingefügt
        if(groupItem != null) {
            binding.title.text = "Gruppe bearbeiten"
            val editable = Editable.Factory.getInstance()
            binding.groupName.text = editable.newEditable(groupItem!!.groupName)
        }
        // Andernfalls wird der Titel auf "Neue Gruppe" gesetzt
        else {
            binding.title.text = "Neue Gruppe"
        }

        // Der OnClickListener für den Button "gruppeSpeichernButton" wird festgelegt
        binding.gruppeSpeichernButton.setOnClickListener{
            saveAction()
        }
    }

    // Die Methode onCreateView() wird aufgerufen, um die Ansicht zu erstellen.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewGroupSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Die Methode saveAction() wird aufgerufen, wenn der Benutzer auf den Button "gruppeSpeichernButton" klickt.
    private fun saveAction() {
        // Der Name der Gruppe wird aus dem Textfeld "groupName" gelesen
        val groupName = binding.groupName.text.toString()

        // Wenn das Feld groupItem null ist, wird eine neue Gruppe erstellt und zur Datenbank hinzugefügt
        if(groupItem == null)
        {
            val newGroupItem = GroupItem(groupName)
            groupViewModel.addGroup(newGroupItem)
        }
        // Andernfalls wird die vorhandene Gruppe aktualisiert
        else {
            groupItem!!.groupName = groupName
            groupViewModel.updateGruppe(groupItem!!)
        }

        // Das Textfeld "groupName" wird geleert und das Fragment geschlossen.
        binding.groupName.setText("")
        dismiss()
    }
}
