package com.example.gruppenmeister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.gruppenmeister.databinding.ActivityMainBinding
import com.example.gruppenmeister.groups.Groups
import com.example.gruppenmeister.todos.Tasks

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        supportActionBar?.hide() // Hide the action bar

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(Home()) // Replace fragment with Home fragment
                R.id.todo -> replaceFragment(Tasks()) // Replace fragment with Todo fragment
                R.id.groups -> replaceFragment(Groups()) // Replace fragment with Groups fragment
                else -> {} // Do nothing if the selected item is unknown
            }
            true // Return true to indicate that the item selection is handled
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment) // Replace the current fragment with the specified fragment
        fragmentTransaction.commit() // Commit the fragment transaction
    }
}