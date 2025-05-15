package com.example.aulaactivityfragment

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DetalhesActivity : AppCompatActivity() {

    private lateinit var dbHelper: NotasDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        val tituloInput = findViewById<EditText>(R.id.inputTitulo)
        val descricaoInput = findViewById<EditText>(R.id.inputDescricao)
        val spinnerTipo = findViewById<Spinner>(R.id.spinnerTipo)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)

        dbHelper = NotasDataBaseHelper(this)

        val opcoes = arrayOf("Urgente", "Não Urgente")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opcoes)
        spinnerTipo.adapter = adapter

        btnSalvar.setOnClickListener {
            val titulo = tituloInput.text.toString().trim()
            val descricao = descricaoInput.text.toString().trim()
            val tipo = spinnerTipo.selectedItem.toString()

            if (titulo.isEmpty() || descricao.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val novaNota = Nota(id = 0, titulo = titulo, descricao = descricao, tipo = tipo)
            val resultado = dbHelper.salvarNota(novaNota)

            Toast.makeText(this, "Nota salva com sucesso!", Toast.LENGTH_SHORT).show()
            finish()

            val intent = Intent().apply {
                putExtra("titulo", titulo)
                putExtra("descricao", descricao)
                putExtra("tipo", tipo)
            }
            setResult(RESULT_OK, intent)
            finish()

        }
    }
}