package com.example.gruppenmeister

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


class TodoActivity : AppCompatActivity() {



    private val GruppenmeisterDatabase by lazy  {gruppenmeisterDatabase.getDatabase(this).AufgabeDao()}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_todo)
        testDaten()


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
        mFragmentTransaction.add(R.id.Beschreibung, mFragment)

        val undle = Bundle()
        undle.putInt("prio", Aufgabe.prio)
        mFragment.arguments = undle
        mFragmentTransaction.add(R.id.Prio, mFragment)

        mFragmentTransaction.commit()
    }




    private fun testDaten() {
        lifecycleScope.launch {
            GruppenmeisterDatabase.testdaten()
        }
    }


}