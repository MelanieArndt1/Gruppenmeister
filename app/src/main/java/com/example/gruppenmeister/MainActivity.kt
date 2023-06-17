package com.example.gruppenmeister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.gruppenmeister.databinding.ActivityMainBinding
import com.example.gruppenmeister.groups.Groups

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())


        binding.bottomNavigationView.setOnItemSelectedListener {

            val changePage = Intent(this, AddActivity::class.java)
            when(it.itemId){
                R.id.home -> replaceFragment(Home())
                R.id.todo -> replaceFragment(Todo())
                R.id.groups -> replaceFragment(Groups())
                R.id.add -> startActivity(changePage)

                else ->{



                }

            }

            true

        }


    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()


    }
}