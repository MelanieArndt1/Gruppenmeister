package com.example.gruppenmeister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class Todo : Fragment() {


    private lateinit var beschreibung: TextView
    private lateinit var name: TextView
    private lateinit var prio: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_todo, container, false)

        beschreibung = view.findViewById<View>(R.id.Beschreibung) as TextView
        name = view.findViewById<View>(R.id.Name) as TextView
        prio = view.findViewById<View>(R.id.Prio) as TextView

        val namebundle = arguments
        val namemessage = namebundle!!.getString("name")

        val beschreibungbundle = arguments
        val beschreibungmessage = beschreibungbundle!!.getString("beschreibung")

        val priobundle = arguments
        val priomessage = priobundle!!.getInt("prio")

        name.text = namemessage
        beschreibung.text = beschreibungmessage
        prio.text = priomessage.toString()

        return view

    }

}