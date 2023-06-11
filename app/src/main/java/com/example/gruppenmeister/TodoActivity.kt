package com.example.gruppenmeister

import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class TodoActivity : AppCompatActivity() {



    private val GruppenmeisterDatabase by lazy  {gruppenmeisterDatabase.getDatabase(this).AufgabeDao()}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_todo)
        observeAufgabe()
        testDaten()

        val name = findViewById<TextView>(R.id.Name)
        val beschreibung = findViewById<TextView>(R.id.Beschreibung)
        val prio = findViewById<TextView>(R.id.Prio)

        val Aufgabe: Aufgabe = GruppenmeisterDatabase.getAufgabe()

        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val mFragment = Todo()

        val mbundle = Bundle()
        mbundle.putString("name", Aufgabe.name)
        mFragment.arguments = mbundle
        mFragmentTransaction.add(R.id.Name, mFragment)

        val bundle = Bundle()
        bundle.putString("beschreibung", Aufgabe.beschreibung)
        mFragment.arguments = bundle
        mFragmentTransaction.add(R.id.Name, mFragment)

        val undle = Bundle()
        undle.putInt("prio", Aufgabe.prio)
        mFragment.arguments = undle
        mFragmentTransaction.add(R.id.Name, mFragment)

        mFragmentTransaction.commit()
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