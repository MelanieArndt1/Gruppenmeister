package com.example.gruppenmeister.groups

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gruppenmeister.databinding.GroupItemCellBinding

// Die Klasse GroupAdapter erweitert die Klasse RecyclerView.Adapter<GroupViewHolder> und wird verwendet, um die Gruppenliste anzuzeigen
class GroupAdapter(
    private val groupItems: List<GroupItem>,
    private val clickListener: GroupItemClickListener
) : RecyclerView.Adapter<GroupViewHolder>() {

    // Die Methode onCreateViewHolder() wird verwendet, um eine neue Instanz von GroupViewHolder zu erstellen
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = GroupItemCellBinding.inflate(from, parent, false)
        return GroupViewHolder(parent.context, binding, clickListener)
    }

    // Die Methode getItemCount() gibt die Anzahl der Elemente in der Liste zurück
    override fun getItemCount(): Int = groupItems.size

    // Die Methode onBindViewHolder() wird verwendet, um die Daten an die Ansicht zu binden
    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bindGroupItem(groupItems[position])
    }
}
