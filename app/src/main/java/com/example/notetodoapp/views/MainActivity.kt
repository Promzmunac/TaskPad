package com.example.notetodoapp.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.notetodoapp.R
import com.example.notetodoapp.databinding.ActivityMainBinding
import com.example.notetodoapp.views.fragments.NoteFragment
import com.example.notetodoapp.views.fragments.TodoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNav = binding.bnvNav

        loadFragment(NoteFragment())

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.noteFragment -> { loadFragment(NoteFragment())
                    true
                }

                R.id.todoFragment -> { loadFragment(TodoFragment())
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment).addToBackStack(null).commit()
            }
    }
}