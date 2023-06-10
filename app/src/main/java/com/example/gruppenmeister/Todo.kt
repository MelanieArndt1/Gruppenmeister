package com.example.gruppenmeister

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.fragment.app.Fragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Todo : AppCompatActivity() {



    private val GruppenmeisterDatabase by lazy  {gruppenmeisterDatabase.getDatabase(this).AufgabeDao()}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_todo)
        observeAufgabe()
        testDaten()
    }





    private fun observeAufgabe() {
        lifecycleScope.launch {
             GruppenmeisterDatabase.getAufgabe()
            }
    }

    private fun testDaten() {
        lifecycleScope.launch {
            GruppenmeisterDatabase.testdaten()
        }
    }


}