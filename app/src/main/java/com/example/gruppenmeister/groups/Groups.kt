package com.example.gruppenmeister.groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gruppenmeister.GroupMasterApplication
import com.example.gruppenmeister.R
import com.example.gruppenmeister.databinding.FragmentGroupsBinding

// Die Klasse Groups erbt von Fragment und implementiert die GroupItemClickListener-Schnittstelle
class Groups : Fragment(), GroupItemClickListener {

    // Die Variable binding enthält eine Instanz der Klasse FragmentGroupsBinding
    private lateinit var binding: FragmentGroupsBinding

    // Die Variable groupViewModel enthält eine Instanz der Klasse GroupViewModel
    private val groupViewModel: GroupViewModel by viewModels {
        val activity= requireActivity()
        GroupItemModelFactory((activity.application as GroupMasterApplication).groupRepository)
    }

    // Die Methode onViewCreated() wird verwendet, um die Ansicht zu erstellen und den OnClickListener für den Button "alphaSort" festzulegen
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Die Variablen toBeSorted und isSorted werden initialisiert
        var toBeSorted = true
        var isSorted = false

        // Der OnClickListener für den Button "alphaSort" wird festgelegt
        binding.alphaSort.setOnClickListener {
            groupViewModel.gruppen.observe(viewLifecycleOwner) { original ->
                var origin = original
                var list = original.toMutableList()

                // Wenn die Liste noch nicht sortiert ist und soritert werden soll, wird die Liste alphabetisch sortiert
                // Darstellung des Buttons wird geändert, um Aktivierung sichtbar zu machen
                if(isSorted == false && toBeSorted == true) {
                    list.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.groupName }))
                    list.toList()
                    groupViewModel.showGruppen.value = list
                    isSorted = true
                    binding.alphaSort.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    binding.alphaSort.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))

                    // Wenn die Liste bereits sortiert ist und umsortiert werden soll, wird die Liste alphabetisch umgekehrt sortiert
                    // Darstellung des Buttons bleibt geändert, um Aktivierung sichtbar zu machen
                }else if(isSorted == true && toBeSorted == true){
                    list.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.groupName }))
                    list.reverse()
                    list.toList()
                    groupViewModel.showGruppen.value = list
                    isSorted = false
                    toBeSorted = false
                    binding.alphaSort.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    binding.alphaSort.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))

                    // Wenn die Liste nicht weiter sortiert werden soll, wird die ursprüngliche Liste angezeigt
                    // Darstellungs des Buttons wird auf ursprüngliche Darstellung zurückgesetzt, um Deaktivierung sichtbar zu machen
                }else if(toBeSorted == false){
                    groupViewModel.showGruppen.value = origin
                    toBeSorted = true
                    binding.alphaSort.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
                    binding.alphaSort.setBackgroundColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
                }
            }
            updateRecyclerView()
        }

        // Der OnClickListener für den Button "newGroupButton" wird festgelegt und die Methode setRecyclerView() wird aufgerufen
        binding.newGroupButton.setOnClickListener{
                val newGroupSheet = NewGroupSheet(null)
            newGroupSheet.show(childFragmentManager,"newGroupTag")
            }
        setRecyclerView()
    }

    // Die Methode onCreateView() wird verwendet, um die Ansicht für das Fragment zu erstellen
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupsBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Die Methode setRecyclerView() wird verwendet, um den RecyclerView für die Gruppenliste festzulegen
    fun setRecyclerView(){
        val activity= requireActivity()
        groupViewModel.gruppen.observe(viewLifecycleOwner){
        binding.groupListRecyclerView.apply{
            layoutManager = LinearLayoutManager(activity.applicationContext)
            adapter = GroupAdapter(it, this@Groups)
        }
        }
    }

    // Die Methode updateRecyclerView() wird verwendet, um den RecyclerView für die gefilterte Gruppenliste festzulegen
    fun updateRecyclerView(){
        val activity= requireActivity()
        groupViewModel.showGruppen.observe(viewLifecycleOwner){
            binding.groupListRecyclerView.apply{
                layoutManager = LinearLayoutManager(activity.applicationContext)
                adapter = GroupAdapter(it, this@Groups)
            }
        }
    }

    // Die Methode editGroupItem() wird verwendet, um ein Dialogfragment zum Bearbeiten einer Gruppe anzuzeigen
    override fun editGroupItem(groupItem: GroupItem) {
        NewGroupSheet(groupItem).show(childFragmentManager,"newGroupTag")
    }

    // Die Methode moreAction() wird verwendet, um ein Menü-Fenster zur Auswahl von Bearbeiten oder Löschen anzuzeigen und die entsprechende Funktion auszuwählen
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