package com.example.aulaactivityfragment

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class UrgentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_urgent)

        val titulo = intent.getStringExtra("titulo") ?: ""
        val descricao = intent.getStringExtra("descricao") ?: ""

        val bundle = Bundle().apply {
            putString("titulo", titulo)
            putString("descricao", descricao)
        }

        val fragment = UrgentFragment().apply {
            arguments = bundle
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
    }

