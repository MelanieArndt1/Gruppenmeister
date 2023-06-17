package com.example.gruppenmeister

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.gruppenmeister.databinding.GroupItemCellBinding

class GroupViewHolder(
    private val context: Context,
    private val binding: GroupItemCellBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bindGroupItem(groupItem: Gruppe){
        binding.groupName.text = groupItem.groupName
    }
}