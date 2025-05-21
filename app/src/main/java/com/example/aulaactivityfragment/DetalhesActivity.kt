package com.example.aulaactivityfragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DetalhesActivity : AppCompatActivity() {

    private lateinit var dbHelper: NotasDataBaseHelper
    private var notaId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        val editTitulo = findViewById<EditText>(R.id.editTitulo)
        val editDescricao = findViewById<EditText>(R.id.editDescricao)
        val spinnerTipo = findViewById<Spinner>(R.id.spinnerTipo)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)

        dbHelper = NotasDataBaseHelper(this)

        val tipos = arrayOf("Urgente", "Não Urgente")
        spinnerTipo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipos)

        // Verificar se é edição
        notaId = intent.getIntExtra("id", 0)
        if (notaId != 0) {
            editTitulo.setText(intent.getStringExtra("titulo"))
            editDescricao.setText(intent.getStringExtra("descricao"))
            val tipo = intent.getStringExtra("tipo")
            val pos = tipos.indexOf(tipo)
            spinnerTipo.setSelection(if (pos >= 0) pos else 0)
        }

        btnSalvar.setOnClickListener {
            val titulo = editTitulo.text.toString()
            val descricao = editDescricao.text.toString()
            val tipo = spinnerTipo.selectedItem.toString()

            if (titulo.isEmpty() || descricao.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (notaId == 0) {
                val nota = Nota(titulo = titulo, descricao = descricao, tipo = tipo)
                dbHelper.salvarNota(nota)
                Toast.makeText(this, "Nota adicionada!", Toast.LENGTH_SHORT).show()
            } else {
                val nota = Nota(id = notaId, titulo = titulo, descricao = descricao, tipo = tipo)
                dbHelper.atualizarNota(nota)
                Toast.makeText(this, "Nota atualizada!", Toast.LENGTH_SHORT).show()
            }

            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}