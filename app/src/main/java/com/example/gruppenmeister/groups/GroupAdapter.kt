package com.example.gruppenmeister.groups

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gruppenmeister.databinding.GroupItemCellBinding

class GroupAdapter(
    private val groupItems: List<GroupItem>,
    private val clickListener: GroupItemClickListener
) : RecyclerView.Adapter<GroupViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = GroupItemCellBinding.inflate(from, parent, false)
        return GroupViewHolder(parent.context, binding, clickListener)
    }

    override fun getItemCount(): Int = groupItems.size

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bindGroupItem(groupItems[position])
    }
}