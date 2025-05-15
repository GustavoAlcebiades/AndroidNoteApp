package com.example.aulaactivityfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import android.util.Log
import android.widget.TextView

class UrgentFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_urgent, container, false)
        val tituloView = view.findViewById<TextView>(R.id.txtTitulo)
        val descricaoView = view.findViewById<TextView>(R.id.txtDescricao)

        val titulo = arguments?.getString("titulo") ?: "Sem título"
        val descricao = arguments?.getString("descricao") ?: "Sem descrição"

        tituloView.text = titulo
        descricaoView.text = descricao

        return view
    }
}
