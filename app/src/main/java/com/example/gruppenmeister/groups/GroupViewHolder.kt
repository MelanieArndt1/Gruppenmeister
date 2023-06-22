package com.example.gruppenmeister.groups

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.gruppenmeister.databinding.GroupItemCellBinding

// Die Klasse GroupViewHolder erbt von RecyclerView.ViewHolder
class GroupViewHolder(
    private val context: Context,
    private val binding: GroupItemCellBinding,
    private val clickListener: GroupItemClickListener
): RecyclerView.ViewHolder(binding.root) {

    // Die Methode bindGroupItem() wird verwendet, um eine Gruppe anzuzeigen und den OnClickListener für die Ansicht zu setzen
    fun bindGroupItem(groupItem: GroupItem){
        binding.groupName.text = groupItem.groupName

        // Der OnClickListener für die Ansicht "groupCellContainer" wird festgelegt
        binding.groupCellContainer.setOnClickListener{
            clickListener.editGroupItem(groupItem)
        }

        // Der OnClickListener für das Symbol "moreIcon" wird festgelegt
        binding.moreIcon.setOnClickListener{
            clickListener.moreAction(groupItem, it)
        }
    }
}
