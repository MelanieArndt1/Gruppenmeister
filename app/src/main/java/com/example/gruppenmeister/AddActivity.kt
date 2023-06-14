package com.example.gruppenmeister

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AddActivity: AppCompatActivity() {

    val list = mutableListOf<String>("test")

    @SuppressLint("ResourceType", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add)

        val button = findViewById<Button>(R.id.button)
        val editText = findViewById<EditText>(R.id.editText)
        val view1 = findViewById<TextView>(R.id.view1)
        val view2 = findViewById<TextView>(R.id.view2)
        val view3 = findViewById<TextView>(R.id.view3)
        val view4 = findViewById<TextView>(R.id.view4)
        val adapter = ArrayAdapter(this, R.layout.fragment_add, list)

        Log.i("", adapter.toString())

        button.setOnClickListener {
            if(editText.text.isNotEmpty()) {
                list.add(editText.text.toString())
                Log.i("", list.toString())
                editText.setText("")

                if(list.count() == 1){
                    view1.text = list[0]
                }

                if(list.count() == 2){
                    view2.text = list[1]}

                if(list.count() == 3){
                    view3.text = list[2]}

                if(list.count() == 4){
                    view4.text = list[3]
                }
            }
        }

    }
  }