package com.example.aulaactivityfragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotasActivity : AppCompatActivity() {
    private val REQUEST_CODE_DETALHES = 1
    private val listaNotasUrgentes = mutableListOf<Nota>()
    private val listaNotasNaoUrgentes = mutableListOf<Nota>()
    private lateinit var adapterUrgentes: NotasAdapter
    private lateinit var adapterNaoUrgentes: NotasAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

        val listViewNotasUrgentes = findViewById<ListView>(R.id.listViewNotasUrgentes)
        val listViewNotasNaoUrgentes = findViewById<ListView>(R.id.listViewNotasNaoUrgentes)
        val btnAdicionar = findViewById<FloatingActionButton>(R.id.btnAdicionar)

        adapterUrgentes = NotasAdapter(this, listaNotasUrgentes) { nota ->
            deletarNota(nota)
        }
        adapterNaoUrgentes = NotasAdapter(this, listaNotasNaoUrgentes) { nota ->
            deletarNota(nota)
        }

        listViewNotasUrgentes.adapter = adapterUrgentes
        listViewNotasNaoUrgentes.adapter = adapterNaoUrgentes

        carregarNotasDoBanco()

        btnAdicionar.setOnClickListener {
            val intent = Intent(this, DetalhesActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_DETALHES)
        }
    }

    private fun deletarNota(nota: Nota) {
        val dbHelper = NotasDataBaseHelper(this)
        val linhasDeletadas = dbHelper.deletarNota(nota.id.toLong())

        if (linhasDeletadas > 0) {
            if (nota.tipo == "Urgente") {
                listaNotasUrgentes.remove(nota)
                adapterUrgentes.notifyDataSetChanged()
            } else {
                listaNotasNaoUrgentes.remove(nota)
                adapterNaoUrgentes.notifyDataSetChanged()
            }
            Toast.makeText(this, "Nota deletada", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Erro ao deletar", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        carregarNotasDoBanco()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_DETALHES && resultCode == RESULT_OK) {
            val titulo = data?.getStringExtra("titulo") ?: return
            val descricao = data?.getStringExtra("descricao") ?: return
            val tipo = data?.getStringExtra("tipo") ?: "Não Urgente"

            val novaNota = Nota(titulo = titulo, descricao = descricao, tipo = tipo)

            // Salva no banco
            val dbHelper = NotasDataBaseHelper(this)
            dbHelper.salvarNota(novaNota)

            // Recarrega a lista para atualizar a UI
            carregarNotasDoBanco()
        }
    }

    private fun carregarNotasDoBanco() {
        val dbHelper = NotasDataBaseHelper(this)
        val todasNotas = dbHelper.listarNotas()

        listaNotasUrgentes.clear()
        listaNotasNaoUrgentes.clear()

        todasNotas.forEach {
            if (it.tipo == "Urgente") {
                listaNotasUrgentes.add(it)
            } else {
                listaNotasNaoUrgentes.add(it)
            }
        }

        atualizarAdapters()
    }


    private fun atualizarAdapters() {

        adapterUrgentes.atualizarNotas(listaNotasUrgentes)

        adapterNaoUrgentes.atualizarNotas(listaNotasNaoUrgentes)

    }
}
