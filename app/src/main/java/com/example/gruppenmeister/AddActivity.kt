package com.example.gruppenmeister

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class AddActivity: AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add)

        val list = mutableListOf<String>("test")

        val button = findViewById<Button>(R.id.button)
        val listView = findViewById<ListView>(R.id.list)
        val editText = findViewById<EditText>(R.id.editText)
        val adapter = ArrayAdapter(this, R.id.list, list)

        Log.i("", adapter.toString())
        listView.adapter = adapter

        button.setOnClickListener {
            if(editText.text.isNotEmpty()) {
                list.add(editText.text.toString())
                Log.i("", list.toString())
                editText.setText("")
            }
        }

    }
  }