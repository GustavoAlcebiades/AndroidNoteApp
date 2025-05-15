package com.example.aulaactivityfragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity()
{
    lateinit var buttonCadastrar: Button
    lateinit var nomeTexto: EditText
    lateinit var emailTexto: EditText
    lateinit var senhaTexto: EditText

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        nomeTexto = findViewById(R.id.nomeText)
        emailTexto = findViewById(R.id.emailText)
        senhaTexto = findViewById(R.id.senhaText)
        buttonCadastrar = findViewById(R.id.buttonCadastrar)
        buttonCadastrar.setOnClickListener {
            val intent = Intent(this, NotasActivity::class.java)
            //Passar parâmetros para outra Activity
//            val nome = nomeTexto.text.toString()
//            val email = emailTexto.text.toString().toIntOrNull() ?: 0 // Evita erro se estiver vazio
//            val senha = senhaTexto.text.toString().toDoubleOrNull() ?: 0.0
//            intent.putExtra("nome", nome)
//            intent.putExtra("email", email)
//            intent.putExtra("senha", senha)
            startActivity(intent)

        }
    }
}

