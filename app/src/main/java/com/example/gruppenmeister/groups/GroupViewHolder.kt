package com.example.gruppenmeister.groups

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.gruppenmeister.databinding.GroupItemCellBinding

class GroupViewHolder(
    private val context: Context,
    private val binding: GroupItemCellBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bindGroupItem(groupItem: GroupItem){
        binding.groupName.text = groupItem.groupName
    }

}