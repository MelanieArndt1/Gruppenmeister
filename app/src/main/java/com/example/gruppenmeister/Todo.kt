package com.example.gruppenmeister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Todo.newInstance] factory method to
 * create an instance of this fragment.
 */
class Todo : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var beschreibung: TextView
    private lateinit var name: TextView
    private lateinit var prio: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Todo.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Todo().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}