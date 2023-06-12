package com.example.gruppenmeister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment

class Add: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view:View = inflater.inflate(R.layout.fragment_add, container, false)

        val list = mutableListOf<String>("test")


        val button = view.findViewById<Button>(R.id.button)
        val editText = view.findViewById<EditText>(R.id.editText)
        val listView = view.findViewById<ListView>(R.id.list)


        button.setOnClickListener {
            if(editText.text.isNotEmpty()) {
                list.add(editText.text.toString())
                editText.setText("")
                Log.i("", list.toString())
            }
        }

        return view
    }
}