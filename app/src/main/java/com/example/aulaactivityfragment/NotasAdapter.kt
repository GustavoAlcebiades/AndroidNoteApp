package com.example.aulaactivityfragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class NotasAdapter(
    private val context: Context,
    private var notas: List<Nota>,
    private val onDeleteClicked: (Nota) -> Unit
) : ArrayAdapter<Nota>(context, 0, notas) {

    fun atualizarNotas(novasNotas: List<Nota>) {
         this.notas = novasNotas
        notifyDataSetChanged()
    }

    override fun getCount(): Int = notas.size

    override fun getItem(position: Int): Nota? = notas[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_nota, parent, false)

        val nota = notas[position]

        val tituloTextView = view.findViewById<TextView>(R.id.textTituloNota)
        val descricaoTextView = view.findViewById<TextView>(R.id.textDescricao)
        val btnDeletar = view.findViewById<ImageButton>(R.id.btnDeletarNota)

        tituloTextView.text = "${position + 1}. ${nota.titulo}"
        descricaoTextView.text = nota.descricao

        btnDeletar.setOnClickListener {
            onDeleteClicked(nota)
        }

        return view
    }
}
