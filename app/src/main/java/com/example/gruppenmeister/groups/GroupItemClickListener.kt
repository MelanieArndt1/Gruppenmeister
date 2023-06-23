package com.example.gruppenmeister.groups

import android.view.View

//Interface für die ClickListener-Funktionalitäten
interface GroupItemClickListener
{
    fun editGroupItem (groupItem: GroupItem)
    fun moreAction (groupItem: GroupItem, view: View)
}