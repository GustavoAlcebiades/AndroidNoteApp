package com.example.aulaactivityfragment

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class Nota(val id: Int = 0, val titulo: String, val descricao: String, val tipo: String)

 class NotasDataBaseHelper(context: Context) : SQLiteOpenHelper(context, "NotasDB", null, 1) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(
                """
            CREATE TABLE notas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                descricao TEXT NOT NULL,
                tipo TEXT NOT NULL
            )
            """
            )
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS notas")
            onCreate(db)
        }

     fun salvarNota(nota: Nota) {
         val db = writableDatabase
         val values = ContentValues().apply {
             put(COLUMN_TITULO, nota.titulo)
             put(COLUMN_DESCRICAO, nota.descricao)
             put(COLUMN_TIPO, nota.tipo)
         }
         db.insert(TABLE_NAME, null, values)
         db.close()
     }



     fun listarNotas(): List<Nota> {
         val notas = mutableListOf<Nota>()
         val db = readableDatabase
         val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

         if (cursor.moveToFirst()) {
             do {
                 val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                 val titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO))
                 val descricao = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRICAO))
                 val tipo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPO))

                 val nota = Nota(id, titulo, descricao, tipo)
                 notas.add(nota)
             } while (cursor.moveToNext())
         }

         cursor.close()
         db.close()
         return notas
     }

     fun deletarNota(id: Long): Int {
         val db = writableDatabase

         val linhasDeletadas = db.delete("Notas", "id = ?", arrayOf(id.toString()))
         db.close()
         return linhasDeletadas
     }

     companion object {
         const val DATABASE_NAME = "notas.db"
         const val DATABASE_VERSION = 1
         const val TABLE_NAME = "notas"
         const val COLUMN_ID = "id"
         const val COLUMN_TITULO = "titulo"
         const val COLUMN_DESCRICAO = "descricao"
         const val COLUMN_TIPO = "tipo"
     }


 }