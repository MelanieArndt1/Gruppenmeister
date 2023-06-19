package com.example.gruppenmeister.groups

import android.view.View

interface GroupItemClickListener
{
    fun editGroupItem (groupItem: GroupItem)
    fun moreAction (groupItem: GroupItem, view: View)
}