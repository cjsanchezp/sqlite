package com.crisape.sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.crisape.sqlite.modelo.LibroDAO
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListarActivity : AppCompatActivity() {
    private lateinit var btnNuevo : FloatingActionButton
    private lateinit var daoLibro: LibroDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar)
        asignarReferencias()
        mostrarLibros()
    }

    private fun mostrarLibros(){
        val listarLibros = daoLibro.cargarLibros()
        Log.d("==>", "${listarLibros.size}")
    }


    private fun asignarReferencias(){
        daoLibro = LibroDAO(this)
        btnNuevo = findViewById(R.id.btnNuevo)
        btnNuevo.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}