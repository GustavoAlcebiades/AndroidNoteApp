package com.example.aulaactivityfragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotasActivity : AppCompatActivity() {
    private val REQUEST_CODE_DETALHES = 1
    private val REQUEST_CODE_EDITAR = 2
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

        adapterUrgentes = NotasAdapter(
            this,
            listaNotasUrgentes,
            onDeleteClicked = { nota -> deletarNota(nota) },
            onEditClicked = { nota -> editarNota(nota) }
        )

        adapterNaoUrgentes = NotasAdapter(
            this,
            listaNotasNaoUrgentes,
            onDeleteClicked = { nota -> deletarNota(nota) },
            onEditClicked = { nota -> editarNota(nota) }
        )

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

    private fun editarNota(nota: Nota) {
        val intent = Intent(this, DetalhesActivity::class.java).apply {
            putExtra("id", nota.id)
            putExtra("titulo", nota.titulo)
            putExtra("descricao", nota.descricao)
            putExtra("tipo", nota.tipo)
        }
        startActivityForResult(intent, REQUEST_CODE_EDITAR)
    }

    override fun onResume() {
        super.onResume()
        carregarNotasDoBanco()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
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


    ajustarAlturaListView(findViewById(R.id.listViewNotasUrgentes))
    ajustarAlturaListView(findViewById(R.id.listViewNotasNaoUrgentes))

}

    fun ajustarAlturaListView(listView: ListView) {
        val listAdapter = listView.adapter ?: return

        var totalHeight = 0
        for (i in 0 until listAdapter.count) {
            val listItem = listAdapter.getView(i, null, listView)
            listItem.measure(
                View.MeasureSpec.makeMeasureSpec(listView.width, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            totalHeight += listItem.measuredHeight
        }

        val params = listView.layoutParams
        params.height = totalHeight + (listView.dividerHeight * (listAdapter.count - 1))
        listView.layoutParams = params
        listView.requestLayout()
    }


}

