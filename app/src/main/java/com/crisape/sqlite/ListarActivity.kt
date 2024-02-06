package com.crisape.sqlite

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crisape.sqlite.entidades.Libro
import com.crisape.sqlite.modelo.LibroDAO
import com.crisape.sqlite.util.SqliteHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListarActivity : AppCompatActivity() {
    private lateinit var btnNuevo : FloatingActionButton
    private lateinit var daoLibro: LibroDAO
    private lateinit var rvLibros : RecyclerView
    private var adaptador : AdaptadorPersonalizado? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar)
        asignarReferencias()
        mostrarLibros()
    }

    private fun mostrarLibros(){
        val listarLibros = daoLibro.cargarLibros()
        Log.d("==>", "${listarLibros.size}")
        adaptador?.contexto(this)
        adaptador?.addItems(listarLibros)
    }


    private fun asignarReferencias(){
        daoLibro = LibroDAO(this)
        btnNuevo = findViewById(R.id.btnNuevo)
        btnNuevo.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        rvLibros = findViewById(R.id.rvLibros)
        rvLibros.layoutManager = LinearLayoutManager(this)
        adaptador = AdaptadorPersonalizado()
        rvLibros.adapter = adaptador

        adaptador?.setOnclickDeleteItem {
            eliminarLibro(it.id)
        }
    }

    private fun eliminarLibro(id:Int){
        val ventana = AlertDialog.Builder(this)
        ventana.setMessage("Desea eliminar el libro?")
        ventana.setCancelable(true)
        ventana.setPositiveButton("SI"){dialog, _->
            val libroDAO = LibroDAO(this)
            var mensaje = daoLibro.eliminarLibro(id)
            mostrarLibros()
            dialog.dismiss()
            mostrarMensaje(mensaje)
        }
        ventana.setNegativeButton("NO"){dialog,_->
            dialog.dismiss()
        }
        var alert = ventana.create()
        alert.show()
    }

    private fun mostrarMensaje(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Mensaje Informativo")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialogInterface, i ->
            val intent = Intent(this, ListarActivity::class.java)
            startActivity(intent)
        })
        ventana.create().show()
    }
}